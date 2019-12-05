package chapter05.booleanLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 自定义显示锁测试
 */
public class BooleanLockTest {
    private final Lock lock = new BooleanLock();

    //使用try...finally语句块确保lock每次都能被正确释放

    public void syncMethod() {
        try {
            lock.lock();
            int randomInt = current().nextInt(10);
            System.out.println(currentThread() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void syncMethodTimeoutable() {
        try {
            lock.lock(1000);
            int randomInt = current().nextInt(10);
            System.out.println(currentThread() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BooleanLockTest booleanLockTest = new BooleanLockTest();
//        IntStream.range(0,10).mapToObj(i->new Thread(booleanLockTest::syncMethod)).forEach(Thread::start);

//        System.out.println("==========测试可中断特性============");
//        new Thread(booleanLockTest::syncMethod, "T1").start();
//        TimeUnit.MILLISECONDS.sleep(2);
//        Thread t2 = new Thread(booleanLockTest::syncMethod, "T2");
//        t2.start();
//        TimeUnit.MILLISECONDS.sleep(10);
//        //main线程打断T2
//        t2.interrupt();

        System.out.println("============测试等待锁超时============");
        new Thread(booleanLockTest::syncMethodTimeoutable, "T1").start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(booleanLockTest::syncMethodTimeoutable, "T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);
    }
}
