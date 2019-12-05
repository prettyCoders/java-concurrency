package chapter08;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基础线程池实现
 */
public class BasicThreadPool extends Thread implements ThreadPool {

    //初始化线程数量
    private final int initSize;

    //线程池最大线程数量
    private final int maxSize;

    //线程池核心线程数量
    private final int coreSize;

    //当前活跃线程数量
    private int activeCount;

    //创建线程所需工厂
    private final ThreadFactory threadFactory;

    //任务队列
    private final RunnableQueue runnableQueue;

    //线程是否已经被shutdown
    private volatile boolean isShutdown = false;

    //工作线程队列
    private final Queue<ThreadTask> threadTaskQueue = new ArrayDeque<>();

    //默认拒绝策略,该拒绝策略会直接将任务丢弃
    private static final DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    //默认线程工厂
    private static final ThreadFactory DEFAULT_THREAD_FACTIRY = new DefaultThreadFactory();

    private final long keepAliveTime;

    private final TimeUnit timeUnit;

    //初始化线程数量，最大线程数量，核心线程数量，任务队列最大数量
    public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {
        this(initSize, maxSize, coreSize, DEFAULT_THREAD_FACTIRY, queueSize, DEFAULT_DENY_POLICY,
                10, TimeUnit.SECONDS);
    }

    //
    public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactiry,
                           int queueSize, DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactiry;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }

    //初始化线程池
    private void init() {
        start();
        //先创建initSize个线程
        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        //提交任务只是简单的往任务队列里插入Runnable
        runnableQueue.offer(runnable);
    }

    //创建任务线程并启动
    private void newThread() {
        //InternalTask可以看做是一个Runnable,指定 runnableQueue 表示这个线程启动之后会不断从这个任务队列里拿任务
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);

        //构建工作线程以及添加到工作线程队列
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadTaskQueue.offer(threadTask);
        //线程池活跃线程数+1
        this.activeCount++;
        //启动工作线程,不断从 runnableQueue 任务队列里拿任务
        thread.start();
    }

    //从线程池中移除某个线程
    private void removeThread() {
        ThreadTask threadTask = threadTaskQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }

    //run()继承自Thread，主要用于维护线程数量，比如扩容、回收等
    @Override
    public void run() {

        while (!isShutdown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true;
                break;
            }
            //使用同步代码块主要是为了阻止在线程维护过程中线程池销毁引起的数据不一致问题
            synchronized (this) {
                if (isShutdown) {
                    break;
                }

                //当前队列中有任务未处理，并且activeCount<coreSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = activeCount; i < coreSize; i++) {
                        newThread();
                    }
                    //continue的目的在于不想让线程的扩容直接达到maxSize
                    continue;
                }

                //当前队列中有任务未处理，并且activeCount<maxSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    for (int i = coreSize; i < maxSize; i++) {
                        newThread();
                    }
                }

                //如果任务队列中没有任务，则需要回收，回收到coreSize即可
                //需要注意的是，如果被回收的线程恰巧从 Runnable 任务取出了某个任务，则会继续保持该线程的运行
                //直到完成了任务为止，详见 InternalTask 的 run()
                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    for (int i = coreSize; i < activeCount; i++) {
                        removeThread();
                    }
                }
            }
        }
    }

    //ThreadTask 只是 InternalTask 和 Thread 的一个组合
    private static class ThreadTask {
        //线程池关闭的时候会用到，thread.interrupt()
        Thread thread;
        //线程回收以及线程池关闭的时候会用到，internalTask.stop()
        InternalTask internalTask;

        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }

    //线程池的销毁同样需要同步机制保护，主要是为了防止与线程池本身的维护线程引起数据冲突
    @Override
    public void shutdown() {
        synchronized (this) {
            if (isShutdown) return;
            isShutdown = true;
            threadTaskQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutdown) {
            throw new IllegalStateException("The thread pool is destroy");
        }
        return this.runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }

    //默认线程工厂
    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);

        private static final ThreadGroup GROUP = new ThreadGroup("MyThreadPool-" + GROUP_COUNTER.getAndDecrement());

        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(GROUP, runnable, "thread-pool-" + COUNTER.getAndDecrement());
        }
    }
}
