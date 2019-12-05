package chapter23;

import java.util.concurrent.TimeUnit;

/**
 * 无限等待的抽象类 Latch
 */
public abstract class Latch {

    //用于控制多少个线程完成任务时才能打开阀门
    protected int limit;

    public Latch(int limit) {
        this.limit = limit;
    }

    //该方法会使得当前线程无限等待，直到所有线程都完成工作，被阻塞的线程是允许被中断的
    public abstract void await() throws InterruptedException;

    //可超时等待
    public abstract void await(TimeUnit timeUnit,long time) throws InterruptedException,WaitTimeOutException;

    //当任务线程完成工作之后调用此方法将计数器减一
    public abstract void countDown();

    //获取当前还有多少个线程没有完成任务
    public abstract int getUnArrived();
}
