package chapter03;

import java.util.concurrent.TimeUnit;

/**
 * 使用中断线程的方法关闭线程
 */
public class InterruptThreadExit {
    public static void main(String[] args) throws InterruptedException {

        //通过检查interrupt标识来决定是否退出
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("I will start work.");
                while (!isInterrupted()) {
                    //working
                }
                System.out.println("I will be exiting.");
            }
        };

        thread.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("System will be shutdown.");
        thread.interrupt();


        System.out.println("===========================");
        //通过捕获中断信号来决定是否退出
        Thread thread1 = new Thread(() -> {
            System.out.println("I will start work.");
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("I will be exiting.");
        });

        thread1.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("System will be shutdown.");
        thread1.interrupt();
    }
}
