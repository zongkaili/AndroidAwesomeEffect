package com.kelly.practice.design_mode;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者模式 高级版
 *
 * @author zyb
 */
public class ProductConsumerPremium {

    /**
     * 生产者消费者【高并发版】
     * 使用阻塞队列实现
     * 生产和消费过程自动化进行，不需要进行干预
     * 消息中间件底层原理
     */
    static class SourceQueue {
        private volatile boolean flag = true; //默认开启，进行生产+消费
        private final AtomicInteger atomicInteger = new AtomicInteger();

        BlockingQueue<String> blockingQueue;

        // 构造注入，传入接口实现类，可以适配7种阻塞队列
        public SourceQueue(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
            // 利用反射获取传入类
            System.out.println("传入阻塞队列\n" + blockingQueue.getClass().getName() + "\n");
        }

        // 生产线程
        public void product() throws Exception {
            String data;// 数据
            boolean offer;
            while (flag) {
                data = atomicInteger.incrementAndGet() + "";
                offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + " 插入队列,data " + data + " 成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 插入队列,data " + data + " 失败");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println(Thread.currentThread().getName() + " 停止生产 ,flag=" + flag + "生产动作结束");
        }

        // 消费者
        public void consumer() throws Exception {
            String result;
            while (flag) {
                result = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if (result == null || result.equalsIgnoreCase("")) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + " 超过2s没有取到，消费退出");
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " 消费队列 ,result=" + result);
            }
        }

        public void stop() throws Exception {
            this.flag = false;
        }
    }

    public static void main(String[] args) throws Exception {
        SourceQueue sourceQueue = new SourceQueue(new ArrayBlockingQueue<>(10));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    private final AtomicInteger count = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "线程 #" + count.getAndIncrement());
                    }
                });
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " 生产者线程启动");
            try {
                sourceQueue.product();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " 消费者线程启动");
            try {
                sourceQueue.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        TimeUnit.SECONDS.sleep(5);

        System.out.println();
        System.out.println(Thread.currentThread().getName() + " BOSS 停止");
        System.out.println();

        sourceQueue.stop();
    }
}
