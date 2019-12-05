package chapter27.general_active_object;


import java.util.LinkedList;

/**
 * ActiveMessageQueue 对应于 Worker Thread 设计模式中的传送带，主要用于传送调用线程通过 Proxy 提交过来的 MethodMessage，
 * 但是这个传送带允许存放无限的 MethodMessage(没有limit约束，理论上可以放无限多个 MethodMessage 直到堆内存溢出)，
 * 并且当有新的 Message 加入时会通知 ActiveDaemonThread 线程
 */
public class ActiveMessageQueue {
    //用于存放提交的 MethodMessage 消息
    private final LinkedList<ActiveMessage> messages=new LinkedList<>();

    //在创建 ActiveMessageQueue 的同时启动 ActiveDaemonThread 线程用来进行异步的方法执行
    public ActiveMessageQueue(){
        //启动 Worker 线程
        new ActiveDaemonThread(this).start();
    }

    public void offer(ActiveMessage activeMessage){
        synchronized (this){
            messages.addLast(activeMessage);
            //因为只有一个线程负责 take 数据，因此没有必要使用 notifyAll();
            this.notify();
        }
    }

    //take() 主要是被 ActiveDaemonThread 线程使用，当 message 队列为空时，ActiveDaemonThread 线程将会被挂起
    protected ActiveMessage take(){
        synchronized (this){
            //当 MethodMessage 队列中没有 Message 的时候，执行线程进入阻塞队列
            while (messages.isEmpty()){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //获取其中一个 MethodMessage 并且从队列中移除
            return messages.removeFirst();
        }
    }
}
