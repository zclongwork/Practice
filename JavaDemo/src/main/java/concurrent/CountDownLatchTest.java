package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 等待多线程完成的CountDownLatch
 * Author zcl
 * Date 2021/10/19
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        //构造方法 等待N个点完成 这里就传N
        //用法：用在多个线程中，只需要将CountDownLatch的引用传递到线程里，线程任务完成后调用countDown()
        final CountDownLatch latch = new CountDownLatch(2);

        System.out.println("启动任务");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1 开始");
                try {
                    Thread.sleep(13000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1 结束");
                latch.countDown();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2 开始");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2 结束");
                latch.countDown();
            }
        });

        thread1.start();
        thread2.start();

        try {

            //await() 阻塞当前线程，直到计数为0

            //指定时间后就不在阻塞当前线程
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("任务结束");


    }
}
