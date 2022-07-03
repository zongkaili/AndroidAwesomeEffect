package com.kelly.practice.multi_thread;

import java.util.concurrent.CyclicBarrier;

/**
 * author: zongkaili
 * data: 2020/4/6
 * desc: CyclicBarrier的测试用例
 */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier;

    static class CyclicBarrierThread extends Thread {
        public void run() {
            int sleepTime = (int) (Math.random() * 10) * 1000;
            System.out.println(Thread.currentThread().getName() + "在路上了，还需" + sleepTime + "毫秒");
            //等待
            try {
                sleep(sleepTime);
                System.out.println(Thread.currentThread().getName() + "到了");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "到了，await结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("人到齐了，开会吧....");
            }
        });

        for (int i = 0; i < 5; i++) {
            new CyclicBarrierThread().start();
        }
    }
}
