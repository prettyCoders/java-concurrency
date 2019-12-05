package chapter08;

import java.util.concurrent.TimeUnit;

/**
 * 测试自定义线程池
 * 任务提交、线程池数量的动态扩展和回收、线程池的销毁
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        //定义线程池，初始化线程数2、核心线程数4、最大线程数6、任务队列最多允许1000个任务
        final ThreadPool threadPool=new BasicThreadPool(2,6,4,1000);
        //定义20个任务并提交给线程池
        for(int i=0;i<20;i++){
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName()+" is running and done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for(;;){
            //不断输出线程池的信息
            System.out.println("ActiveCount:"+threadPool.getActiveCount());
            System.out.println("QueueSize:"+threadPool.getQueueSize());
            System.out.println("CoreSize:"+threadPool.getCoreSize());
            System.out.println("MaxSize:"+threadPool.getMaxSize());
            System.out.println("=========================================");
            TimeUnit.SECONDS.sleep(5);
        }

//        TimeUnit.SECONDS.sleep(12);
//        threadPool.shutdown();
//
//        Thread.currentThread().join();
    }
}
