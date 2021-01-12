package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，
 * three() 方法在 two() 方法之后被执行
 *
 * 思路：确保程序中 关键部分代码的独占性，就可以防止程序进入不一致的状态。
 */
public class InOrder {

    public static void main(String[] args) {
        for(int i=0;i<10;i++) {
            test();

        }
//        test();
    }

    static void test() {
        final AtomicInteger job1 = new AtomicInteger(0);
        final AtomicInteger job2 = new AtomicInteger(0);

        final Foo foo = new Foo();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                foo.first();
                job1.incrementAndGet();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (job1.get() != 1) {

                }
                foo.second();
                job1.compareAndSet(1, 0);
                job2.incrementAndGet();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (job2.get() != 1) {

                }
                foo.third();
                job2.compareAndSet(1, 0);
            }
        });
        t3.start();
        t2.start();
        t1.start();
    }


    static class Foo {




        public void first() {
            System.out.println(1);
        }

        public void second() {
            System.out.println(2);
        }

        public void third() {
            System.out.println(3);
        }
    }
}
