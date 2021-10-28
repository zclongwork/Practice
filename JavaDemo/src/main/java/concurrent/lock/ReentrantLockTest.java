package concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO Description
 * Author zcl
 * Date 2021/10/23
 */
public class ReentrantLockTest {

    int a = 1;

    ReentrantLock lock = new ReentrantLock();

    public void write() {
        lock.lock();
        try {
            a++;
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            int i = a;
            System.out.println("i = " + i);
        } finally {
            lock.unlock();
        }
    }

}
