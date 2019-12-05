package chapter07;

import java.util.concurrent.TimeUnit;

/**
 * JVM 进程的退出是由于 JVM 进程中没有活跃的非守护线程，或者收到了系统的中断信号。
 * 向 JVM 程序注入一个 Hook 线程，在 JVM 退出的时候，Hook 线程会启动执行，通过 Runtime 可以为JVM注入多个 Hook 线程。
 */
public class ThreadHook {
    public static void main(String[] args){
        //为应用程序注入钩子线程
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("The hook thread 1 is running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The hook thread 1 will exit");
        }));

        //钩子线程可注入多个
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("The hook thread 2 is running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The hook thread 2 will exit");
        }));

        System.out.println("The program will is stopping");
    }
}
