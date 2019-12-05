package chapter24.thread_per_message;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * TaskHandler 用于处理每一个提交的 Request 请求，
 * 由于 TaskHandler 将被 Thread 执行，所以需要实现 Runnable 接口
 */
public class TaskHandler implements Runnable {

    //需要处理的 Request 请求
    private final Request request;

    public TaskHandler(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        System.out.println(currentThread()+" Begin handle "+ request);
        slowly();
        System.out.println(currentThread()+" End handle "+ request);
    }

    private void slowly() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
