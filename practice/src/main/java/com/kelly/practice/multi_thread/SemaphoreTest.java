package com.kelly.practice.multi_thread;

import java.util.concurrent.Semaphore;

/**
 * author: zongkaili
 * data: 2020/4/6
 * desc: Semaphore的测试用例
 */
public class SemaphoreTest {
    /**
     * 停车场
     */
    static class ParkingLot {
        /*信号量*/
        private Semaphore semaphore;

        ParkingLot(int count) {
            semaphore = new Semaphore(count);
        }

        void park() {
            try {
                //获取信号量
                semaphore.acquire();
                long time = (long) (Math.random() * 10) * 1000;
                System.out.println(Thread.currentThread().getName() + "进入停车场，停车" + time + "毫秒...");
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "开出停车场...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    static class Car extends Thread {
        ParkingLot parkingLot;

        Car(ParkingLot parkingLot) {
            this.parkingLot = parkingLot;
        }

        @Override
        public void run() {
            parkingLot.park(); //进入停车场
        }
    }

    public static void main(String[] args) {
        //模拟5辆车停3个停车位的场景
        ParkingLot parkingLot = new ParkingLot(3);

        for (int i = 0; i < 5; i++) {
            new Car(parkingLot).start();
        }
    }

}
