package chapter08;

/**
 * 任务队列，主要用于缓存提交到线程池中的任务
 */
public interface RunnableQueue {
    //当有新的任务进来是，首先会offer到队列中
    void offer(Runnable runnable);

    //工作线程通过take方法获取任务
    Runnable take() throws InterruptedException;

    //获取任务队列中任务的数量
    int size();
}
