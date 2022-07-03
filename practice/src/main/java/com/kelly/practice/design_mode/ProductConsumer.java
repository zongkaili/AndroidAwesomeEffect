package com.kelly.practice.design_mode;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产消费者模式
 *
 * @author zyb
 */
public class ProductConsumer {
    /**
     * 生产者消费者【传统版】
     * synchronized、wait、notify
     * 一个初始值0，两个线程交替操作五轮，一个加一，一个减一
     * 思想：
     * 1.线程操作资源类
     * 2.判断->干活->通知
     */
    static class Data {
        private final int MAX = 10;// 容量
        private int num = 0;

        public synchronized void increment() {
            //判断
            while (num >= MAX) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //干活
            num++;
            System.out.println(Thread.currentThread().getName() + " invoked increment 操作,num=" + num);
            //通知
            this.notifyAll();
        }

        public synchronized void decrement() {
            //判断
            while (num == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //干活
            num--;
            System.out.println(Thread.currentThread().getName() + " invoked decrement 操作,num=" + num);
            //通知
            this.notifyAll();
        }
    }

    /**
     * 生产者消费者【传统版】
     * lock、await、signal
     * 一个初始值0，两个线程交替操作五轮，一个加一，一个减一
     * 思想：
     * 1.线程操作资源类
     * 2.判断->干活->通知
     */
    static class Data2 {
        private int num = 0;
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();

        public void increment() {
            lock.lock();
            try {
                //判断
                while (num != 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //干活
                num++;
                System.out.println(Thread.currentThread().getName() + " invoked increment 操作,num=" + num);
                //通知
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

        public void decrement() {
            lock.tryLock();
            try {
                //判断
                while (num == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //干活
                num--;
                System.out.println(Thread.currentThread().getName() + " invoked decrement 操作,num=" + num);
                //通知
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class LockData {
        private int num = 1; //A:1,B:2,C:3
        private final Lock lock = new ReentrantLock();
        private final Condition c1 = lock.newCondition();
        private final Condition c2 = lock.newCondition();
        private final Condition c3 = lock.newCondition();

        public void print5() {
            lock.lock();
            try {
                //判断
                while (num != 1) {
                    c1.await();
                }
                //干活
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + (i + 1));
                }
                //通知
                num = 2;
                c2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print10() {
            lock.lock();
            try {
                //判断
                while (num != 2) {
                    c2.await();
                }
                //干活
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + (i + 1));
                }
                //通知
                num = 3;
                c3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print15() {
            lock.lock();
            try {
                //判断
                while (num != 3) {
                    c3.await();
                }
                //干活
                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + (i + 1));
                }
                //通知
                num = 1;
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Data data = new Data();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    private final AtomicInteger mCount = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "线程 #" + mCount.getAndIncrement());
                    }
                });
        executor.execute(() -> {
            for (int i = 1; i <= 5; i++) {
                data.increment();
            }
        });
        executor.execute(() -> {
            for (int i = 1; i <= 5; i++) {
                data.decrement();
            }
        });

        // for test
        LockData lockData = new LockData();
        executor.execute(() -> {
            for (int i = 1; i <= 10; i++) {
                lockData.print5();
            }
        });
        executor.execute(() -> {
            for (int i = 1; i <= 10; i++) {
                lockData.print10();
            }
        });
        executor.execute(() -> {
            for (int i = 1; i <= 10; i++) {
                lockData.print15();
            }
        });
    }
}
