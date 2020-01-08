package com.zcl.concurrent;

/**
 * volatile保证可见性，但java运算并不是原子操作
 * 因此以下程序的运行结果不是20000，
 * 此例中保证count++的原子性后即可是程序输出预期的20000
 *
 * Author zcl
 * Date 2020-01-08
 */
public class VolatileTest {

    private static volatile int count = 0;
    private static int THREAD_COUNT = 20;

    public static void increase() {
        count++;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].setName("Thread_" + i);
            threads[i].start();
        }

        //等待所有累加线程都结束 studio运行除了main 线程外还有一个Monitor Ctrl-Break线程 因此这里是2
        while (Thread.activeCount()>2) {

            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lstThreads = new Thread[noThreads];
            currentGroup.enumerate(lstThreads);
            for (int i = 0; i < noThreads; i++) {
                System.out.println("线程号：" + i + " = " + lstThreads[i].getName());
            }

            Thread.yield();
        }

        System.out.println("count:" + count);
    }
}
