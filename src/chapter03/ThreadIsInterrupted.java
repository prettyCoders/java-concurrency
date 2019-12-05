package chapter03;

import java.util.concurrent.TimeUnit;

/**
 * isInterrupted是Thread的成员方法，主要判断当前线程是否被中断，
 * 该方法仅是对interrupt标识的一个判断，并不会影响标识发生任何改变。
 */
public class ThreadIsInterrupted {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            //这里不使用sleep()是因为它是一个可中断方法，会捕获到中断信号，从而干扰程序运行结果(sleep():false,false)
            while (true) {
            }
        });
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("Thread is interrupted? %s\n", thread.isInterrupted());
        thread.interrupt();
        System.out.printf("Thread is interrupted? %s\n", thread.isInterrupted());


        //sleep()会捕获中断信号，并且擦除interrupt标识，因此程序结果总是false
        //可中断方法捕获到中断信号后，为了不影响当前线程中的其他方法的正常执行，将interrupt标识复位
        System.out.println("============================================");
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.printf("Thread is interrupted? %s\n", isInterrupted());//调用这个方法不能用lambda
                    }
                }
            }
        };
        thread1.setDaemon(true);
        thread1.start();

        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("Thread is interrupted? %s\n", thread1.isInterrupted());

        thread1.interrupt();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("Thread is interrupted? %s\n", thread1.isInterrupted());
    }
}
