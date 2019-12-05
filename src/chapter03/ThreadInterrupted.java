package chapter03;

import java.util.concurrent.TimeUnit;

/**
 * interrupted()是一个静态方法，用于判断当前线程是否被中断，但是和成员方法isInterrupted()还是有很大区别，
 * 调用该方法会直接擦除线程的interrupt标识，需要注意的是，如果当前线程被打断了，第一次调用interrupted会返回true,
 * 并且立即擦除线程的interrupt标识，第二次包括以后的调用永远返回false,除非在此期间线程再一次被打断
 */
public class ThreadInterrupted {
    public static void main(String[] args) throws InterruptedException {
        /**
         * ...
         * false
         * false
         * false
         * true
         * false
         * false
         * false
         * ...
         */
        //结果是很多false中间有一个true，也就是2毫秒后的interrupt()出现一次，后面的又一直返回false
//        Thread thread= new Thread(() -> {
//            while (true){
//                System.out.println(Thread.interrupted());
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//
//        TimeUnit.MILLISECONDS.sleep(2);
//        thread.interrupt();


        //如果一个线程在没有执行可中断方法之前就被打断，那么接下来执行可中断方法，会立即被中断。
        //也就是说，如果一个线程设置了interrupt表示，那么接下来的可中断方法会立即被中断
        System.out.println("============================================");
        //判断当前线程是否被中断，这里用成员方法isInterrupted()也不会有影响
        System.out.println("Main thread is interrupted? "+Thread.interrupted());
        //中断当前线程
        Thread.currentThread().interrupt();
        //判断当前线程是否被中断,这里用了成员方法isInterrupted()，原因是interrupted()会立即擦除线程的interrupt标识
        //导致程序运行结果不同（下面的sleep不会被打断）
        System.out.println("Main thread is interrupted? "+Thread.currentThread().isInterrupted());

        //当前线程执行可中断方法
        try {
            TimeUnit.MINUTES.sleep(1);
        }catch (InterruptedException e){
            System.out.println("I will be interrupted still.");
        }
    }


}
