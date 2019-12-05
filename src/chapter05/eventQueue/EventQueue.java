package chapter05.eventQueue;

import java.util.LinkedList;

import static java.lang.Thread.currentThread;

/**
 * 事件队列，该队列有三种状态
 * 1、队列满：最多可容纳多少个Event,好比一个系统最多能够同时受理多少业务一样
 * 2、队列空：当队列所有Event都被处理，并且没有新的Event被提交的时候，队列将是空的状态
 * 3、有Event但是没满：有新的Event提交，但是没有到达队列上限
 */
public class EventQueue {
    private final int max;

    static class Event {

    }

    private final LinkedList<Event> eventQueue = new LinkedList<>();

    private final static int DEFAULT_MAX_EVENT = 100;

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    //向队列添加数据
    public void offer(Event event){
        synchronized (eventQueue){
            //超过容量，则阻塞
            while (eventQueue.size()>=max){
                try {
                    console(" the queue is full.");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //有空余空间则添加，并唤醒其他被阻塞的线程
            console(" the new event is submitted.");
            eventQueue.addLast(event);
            eventQueue.notifyAll();
        }
    }

    //获取队列数据
    public Event take(){
        synchronized (eventQueue){
            //队列空，阻塞
            while (eventQueue.isEmpty()){
                try {
                    console(" the queue is empty.");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //队列有数据，则从队列头获取数据，并唤醒其他被阻塞的线程
            Event event=eventQueue.removeFirst();
            eventQueue.notifyAll();
            console(" the queue "+event+" is handled.");
            return event;
        }
    }

    private void console(String message){
        System.out.printf("%s:%s\n",currentThread().getName(),message);
    }
}
