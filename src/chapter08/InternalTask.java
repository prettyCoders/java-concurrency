package chapter08;

/**
 * InternalTask 是 Runnable 的一个实现，主要用于线程池的内部，
 * 该类会使用到 RunnableQueue,然后不断得从 queue 中取出 Runnable,
 * 并运行 Runnable 的 run()
 */
public class InternalTask implements Runnable {

    //任务队列
    private final RunnableQueue runnableQueue;

    //任务是否处于运行中
    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //如果当前任务为 running 并且没有被中断。则其将不断得从 queue 中获取 runnable 并执行 run()
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = runnableQueue.take();
                task.run();
            }catch (Exception e){
                running=false;
                break;
            }

        }
    }

    //停止当前任务，主要会在线程池的shutdown()中使用
    public void stop(){
        this.running=false;
    }
}
