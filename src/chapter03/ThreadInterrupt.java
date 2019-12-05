package chapter03;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断
 */
public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Oh, i am be Interrupted.");
            }
        });
        thread.start();

        //确保thread启动
        TimeUnit.MILLISECONDS.sleep(2);
        thread.interrupt();
    }
}
