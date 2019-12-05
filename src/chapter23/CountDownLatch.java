package chapter23;

import java.util.concurrent.TimeUnit;


/**
 * 无限等待 Latch 的实现
 */
public class CountDownLatch extends Latch {

    //回调任务，所有子任务完成后执行后续的任务
    private final Runnable runnable;

    public CountDownLatch(int limit,Runnable runnable) {
        super(limit);
        this.runnable=runnable;
    }

    @Override
    public void await() throws InterruptedException {
        synchronized (this) {
            //当 limit>0 时，当前线程进入阻塞状态
            while (limit > 0) {
                this.wait();
            }
        }
        if(null!=runnable){
            runnable.run();
        }
    }

    @Override
    public void await(TimeUnit timeUnit, long time) throws InterruptedException, WaitTimeOutException {
        if (time <= 0) {
            throw new IllegalArgumentException("The time is invalid.");
        }
        //将 time 转换为纳秒，表示距离超时剩余的时间
        long remainingNanos = timeUnit.toNanos(time);
        //等待任务将在 endNanos 纳秒后超时
        final long endNanos = System.nanoTime() + remainingNanos;
        synchronized (this) {
            while (limit > 0) {
                //如果超时则抛出 WaitTimeOutException 异常
                if (TimeUnit.NANOSECONDS.toMillis(remainingNanos) < 0) {
                    throw new WaitTimeOutException("The wait time over specify time.");
                }
                //等待 remainingNanos ，在等待过程中有可能被中断，需要重新计算 remainingNanos
                this.wait(TimeUnit.NANOSECONDS.toMillis(remainingNanos));
                remainingNanos = endNanos - System.nanoTime();
            }
        }
        if(null!=runnable){
            runnable.run();
        }
    }

    @Override
    public void countDown() {
        synchronized (this) {
            if (limit < 0) {
                throw new IllegalStateException("all of task already arrived.");
            }
            //limit 减一，唤醒阻塞的线程
            limit--;
            this.notifyAll();
        }
    }

    @Override
    public int getUnArrived() {
        //多线程环境下不准确，只是一个评估值
        return limit;
    }
}
