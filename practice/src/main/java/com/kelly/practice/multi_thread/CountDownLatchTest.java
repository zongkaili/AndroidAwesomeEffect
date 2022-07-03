package com.kelly.practice.multi_thread;

import java.util.concurrent.CountDownLatch;

/**
 * author: zongkaili
 * data: 2020/4/6
 * desc:CountDownLatch的测试用例
 */
public class CountDownLatchTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(5);
    /**
     * Boss线程，等待员工到达开会
     */
    static class BossThread extends Thread{
        @Override
        public void run() {
            System.out.println("Boss在会议室等待，总共有" + countDownLatch.getCount() + "个人开会...");
            try {
                //Boss等待
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("所有人都到齐了，开会吧...");
        }
    }

    /**
     * Employee线程
     */
    static class EmployeeThread  extends Thread{
        @Override
        public void run() {
            int sleepTime = (int) (Math.random() * 10) * 1000;
            try {
                //模拟员工到达会议室的时间
                System.out.println(Thread.currentThread().getName() + "在路上了，还有" + sleepTime + "毫秒");
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "，到达会议室....");
            //员工到达会议室 count - 1
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args){
        //Boss线程启动
        new BossThread().start();

        for(int i = 0 ; i < countDownLatch.getCount() ; i++){
            new EmployeeThread().start();
        }
    }
}
