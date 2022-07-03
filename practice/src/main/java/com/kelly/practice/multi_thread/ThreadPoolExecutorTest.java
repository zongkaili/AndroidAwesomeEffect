package com.kelly.practice.multi_thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: zongkaili
 * data: 2022/7/3
 * desc: 手动创建线程池
 */
class ThreadPoolExecutorTest {

    static class MyRunnable implements Runnable {
        private int i;

        MyRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
            String time = simpleDateFormat.format(new Date());
            System.out.println(time + " 线程 " + Thread.currentThread().getName() + " 执行任务" + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testThreadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(2, 4, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyRunnable(i));
        }
    }

    public static class ThreadPoolExecutorExample {
        public static void main(String[] args) throws InterruptedException {
            ThreadPoolExecutorTest threadPoolExecutorTest = new ThreadPoolExecutorTest();
            threadPoolExecutorTest.testThreadPoolExecutor();
        }
    }


}
