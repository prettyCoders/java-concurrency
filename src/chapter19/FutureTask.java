package chapter19;

/**
 * FutureTask 是 Future 的一个实现，除了实现 get() 和 done() 以外，
 * 还额外增加了 protected 方法 finish(),该方法只要用于接收任务被完成的通知
 *
 * @param <T>
 */
public class FutureTask<T> implements Future<T> {

    //计算结果
    private T result;

    //任务是否完成
    private boolean isDone = false;

    //定义对象锁
    private final Object LOCK = new Object();

    @Override
    public T get() throws InterruptedException {
        synchronized (LOCK) {
            //当任务还未完成时，调用 get() 会被挂起
            while (!isDone){
                LOCK.wait();
            }
            //返回最终的计算结果
            return result;
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }

    //finish方法主要作用是为 FutureTask 设置计算结果和唤醒因调用 get() 被阻塞的线程
    protected void finish(T result) {
        synchronized (LOCK) {
            //balking 设计模式（条件不满足直接放弃）
            if (isDone) return;
            //计算完成，为result赋值，isDone置为true,唤醒阻塞的线程
            this.result = result;
            this.isDone = true;
            LOCK.notifyAll();
        }
    }
}
