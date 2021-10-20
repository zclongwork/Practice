package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * TODO Description
 * Author zcl
 * Date 2021/10/19
 */
class CyclicBarrierTest2 {

    //Runnable 到达屏障后优先被执行
    static CyclicBarrier c = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("CyclicBarrier runnable");
        }
    });

    public static void main(String[] args) {
        System.out.println("启动任务");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1 开始");

                try {
                    c.await();//告诉CyclicBarrier c 我到达屏障了
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                System.out.println("线程1");
            }


        });

        thread1.start();

        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("线程main");
    }
}
