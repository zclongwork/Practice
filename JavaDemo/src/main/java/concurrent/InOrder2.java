package concurrent;


/**
 * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，
 * three() 方法在 two() 方法之后被执行
 * <p>
 * 思路：确保程序中 关键部分代码的独占性，就可以防止程序进入不一致的状态。
 * wait notify的使用
 *
 */
public class InOrder2 {

    public static void main(String[] args) {
        InOrder2 order2 = new InOrder2();
        order2.test();
    }


    private boolean firstFinished;
    private boolean secondFinished;
    private final Object lock = new Object();

    void test() {

        final Foo foo = new Foo();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    foo.first();
                    firstFinished = true;
                    lock.notifyAll();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (!firstFinished) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    foo.second();
                    secondFinished = true;
                    lock.notifyAll();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (!secondFinished) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    foo.third();
                }
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
