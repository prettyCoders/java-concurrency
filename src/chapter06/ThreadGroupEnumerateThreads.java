package chapter06;

import java.util.concurrent.TimeUnit;

/**
 * ThreadGroup 的 enumerate(Thread[] list,boolean recurse)
 * recurse 如果指定为 true 则将 group 及其子 group 中 active 的线程全部复制到 Thread[] 中,false 不递归子 group
 * <p>
 * ThreadGroup 的 enumerate(Thread[] list) 将 group 及其子 group 中 active 的线程全部复制到 Thread[] 中，
 * 等价于 enumerate(Thread[],true)
 *
 * 注意：enumerate方法获取的线程仅仅是个预估值，并不能保证复制的都是当前group的活跃线程
 * 比如复制之后，某个线程结束了生命周期或者有新的active线程加入了group.再一个就是返回值比 Thread[]更准确点
 */
public class ThreadGroupEnumerateThreads {
    public static void main(String[] args) throws InterruptedException {
        //创建一个group
        ThreadGroup myGroup=new ThreadGroup("MyGroup");
        //创建线程加入MyGroup
        Thread thread=new Thread(myGroup,()->{
           while (true){
               try {
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        },"MyThread");
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2);
        ThreadGroup mainGroup=Thread.currentThread().getThreadGroup();

        //activeCount()会递归所有子group的active线程
        Thread[] list=new Thread[mainGroup.activeCount()];
        //包含mainGroup和myGroup的所有active线程
        int threadCount=mainGroup.enumerate(list);
        System.out.println(threadCount);

        //包含mainGroup的所有active线程
        threadCount=mainGroup.enumerate(list,false);
        System.out.println(threadCount);

    }
}
