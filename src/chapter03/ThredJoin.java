package chapter03;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 线程连接（个人翻译），A join() B，表示A阻塞，直到B线程结束生命周期或者达到给定时间才退出阻塞状态
 * 应用场景
 * 当前线程需要等待某个线程的任务执行完毕或者执行给定时间后才开始执行，那就当前线程join那个线程
 */
public class ThredJoin {
    public static void main(String[] args) throws InterruptedException {
        //创建两个线程
        List<Thread> threads = IntStream.range(1, 3).mapToObj(ThredJoin::create).collect(toList());
        //启动两个线程
        threads.forEach(Thread::start);
        //执行两个线程的join()
        for (Thread thread : threads) {
            //这里的调用方是main线程
            //不join的话，三个线程会交替执行，join则两个线程执行完毕后，主线程才会执行
            thread.join();
        }
        //main线程循环输出
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "#" + i);
            shortSleep();
        }
    }

    //构造一个简单的线程，每个线程只是简单的循环输出
    private static Thread create(int seq) {
        return new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "#" + i);
                shortSleep();
            }
        });
    }

    private static void shortSleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
