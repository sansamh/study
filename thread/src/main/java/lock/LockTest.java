package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0
 * @description: 锁为类属性时 多个线程调用同一个类对象的不同方法 每个方法都使用这个锁 会造成阻塞
 * @author: 侯春兵
 * @Date: 17:17 2018/11/29
 */
public class LockTest {

    private Lock lock = new ReentrantLock();

    public void test1() {
        //同一把锁lock
        try {
            if (lock.tryLock(5000, TimeUnit.MICROSECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + "获得锁 test1()");
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "发生异常 test1()");
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "释放锁 test1()");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void test2() {
        //同一把锁lock
        try {
            if (lock.tryLock(10000, TimeUnit.MICROSECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + "获得锁 test2()");
                    //异常
                    int i = 1 / 0;
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "发生异常 test2()");
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "释放锁 test2()");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread1 implements Runnable {
        private LockTest obj;

        public MyThread1(LockTest lockTest) {
            obj = lockTest;
        }

        @Override
        public void run() {
            try {
                obj.test1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThread2 implements Runnable {
        private LockTest obj;

        public MyThread2(LockTest lockTest) {
            obj = lockTest;
        }

        @Override
        public void run() {
            try {
                obj.test2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 两个线程 同时执行一个对象里两个方法 两个方法用了同一把锁 会阻塞等待
         * 持有锁期间发生异常的情况
         * Thread-0获得锁 test1()
         * Thread-0释放锁 test1()
         * Thread-1获得锁 test2()
         * Thread-1发生异常 test2()
         * Thread-1释放锁 test2()
         */
        LockTest lockTest = new LockTest();
        new Thread(new MyThread1(lockTest)).start();
        Thread.sleep(1000);
        new Thread(new MyThread2(lockTest)).start();
    }
}
