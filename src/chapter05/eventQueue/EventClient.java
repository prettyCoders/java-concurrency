package chapter05.eventQueue;

import java.util.concurrent.TimeUnit;

/**
 *事件队列模拟客户端
 */

public class EventClient {
    public static void main(String[] args){
        final EventQueue queue=new EventQueue();

        new Thread(()->{
            for(;;){
                queue.offer(new EventQueue.Event());
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Producer1").start();

        new Thread(()->{
            for(;;){
                queue.offer(new EventQueue.Event());
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Producer2").start();

        new Thread(()->{
            for(;;){
                queue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer1").start();

        new Thread(()->{
            for(;;){
                queue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer2").start();
    }
}
