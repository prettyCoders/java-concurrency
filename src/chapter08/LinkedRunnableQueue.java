package chapter08;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {

    //任务队列的最大容量，在构造时传入
    private final int limit;

    //若任务队列中的任务已经满了，则需要执行拒绝策略
    private final DenyPolicy denyPolicy;

    //存放任务队列的双向循环列表
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    //线程池
    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableList) {
            if (runnableList.size() >= limit) {
                //无法容纳新的任务，执行拒绝策略
                denyPolicy.reject(runnable, threadPool);
            } else {
                //将任务加入队尾。并唤醒阻塞中的线程
                runnableList.addLast(runnable);
                runnableList.notifyAll();
            }
        }
    }

    /**
     * take() 为同步方法，线程不断从队列中获取 Runnable 任务，当队列为空的时候，工作线程会进入阻塞状态
     * 有可能在阻塞的过程中被中断，为了传递中断信号，需要在catch语句块中将异常抛出，通知上游（InternalTask）
     * @return Runnable
     * @throws InterruptedException 中断异常
     */
    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableList) {
            while (runnableList.isEmpty()) {
                try {
                    //如果任务队列没有可执行的任务，则当前线程会挂起，
                    // 进入 runnableList 关联的monitor waitSet 中等待唤醒（新任务加入）
                    runnableList.wait();
                } catch (InterruptedException e) {
                    //中断时需要将此异常抛出
                    throw e;
                }
            }
            return runnableList.removeFirst();
        }
    }

    /**
     * 返回任务队列里的任务个数
     * @return 任务个数
     */
    @Override
    public int size() {
        return runnableList.size();
    }
}
