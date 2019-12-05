package chapter28;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EventBusTest {

    public static void main(String[] args){
        //定义同步的 EventBus，然后将两个普通的对象注册给bus,当 bus 发送 event 的时候 topic 相同，
        //Event 类型相同的 Subscribe 方法将被执行
//        Bus bus=new EventBus("TestBus");
//        bus.register(new SimpleSubscriber1());
//        bus.register(new SimpleSubscriber2());
//        bus.post("Hello");
//        System.out.println("------------");
//        bus.post("Hello","test");


        //同步 EventBus 有个缺点，若其中一个 subscribe 方法运行时间比较长，则会影响下一个 subscribe 方法的执行
        //因此采用 AsyncEventBus 是另一个比较好的选择
        Bus bus=new AsyncEventBus("TestBus", new EventExceptionHandler() {
            @Override
            public void handle(Throwable cause, EventContext context) {
              cause.printStackTrace();
            }
        },Executors.newFixedThreadPool(10));
        bus.register(new SimpleSubscriber1());
        bus.register(new SimpleSubscriber2());
        bus.post("Hello");
        System.out.println("------------");
        bus.post("Hello", "test");

    }

}
