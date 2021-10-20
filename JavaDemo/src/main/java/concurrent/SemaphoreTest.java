package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 控制并发线程数
 * Author zcl
 * Date 2021/10/19
 */
class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    static ExecutorService threadPool =  Executors.newFixedThreadPool(THREAD_COUNT);

    static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();//获取一个许可，获取前一值将线程阻塞

                        System.out.println("sava data i= " + finalI);
                        Thread.sleep(5000);

                        semaphore.release();//释放一个许可


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        threadPool.shutdown();

    }
}
