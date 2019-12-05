package chapter28;

import java.util.concurrent.Executor;

/**
 * 同步 EventBus 是最核心的一个类，它实现了 Bus 的所有功能，但是该类对 Event 的广播推送采用的是同步的方式
 * 如果想要使用异步的方式进行推送，可以使用 EventBus 的子类 AsyncEventBus
 */
public class EventBus implements Bus {

    /**
     * 用于维护 Subscriber 的注册表
     */
    private final Registry registry=new Registry();

    /**
     * Event Bus 的名字
     */
    private String busName;

    /**
     * 默认 Bus 名字
     */
    private static final String DEFAULT_BUS_NAME="default-bus";

    /**
     * 默认 topic 名字
     */
    private static final String DEFAULT_TOPIC_NAME="default-topic";

    /**
     * 用于分发广播消息到各个 Subscriber 的类
     */
    private final Dispatcher dispatcher;

    public EventBus(){
        this(DEFAULT_BUS_NAME,null,Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(String busName){
        this(busName,null,Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    EventBus(String busName, EventExceptionHandler exceptionHandler, Executor executor){
        this.busName=busName;
        this.dispatcher=Dispatcher.newDispatcher(exceptionHandler,executor);
    }

    public EventBus(EventExceptionHandler exceptionHandler){
        this(DEFAULT_BUS_NAME,exceptionHandler,Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    //将注册 Subscriber 动作直接委托给 Registry
    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    //解除注册同样委托给 Registry
    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    //提交 Event 到默认的 topic
    @Override
    public void post(Object event) {
        this.post(event,DEFAULT_TOPIC_NAME);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this,registry,event,topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
