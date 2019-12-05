package chapter27.simple_acitve_object;

/**
 * 为了让 Proxy 构造透明化，设计一个 Factory 工具类
 */
public final class OrderServiceFactory {

    //将 ActiveMessageQueue 定义成 static 的目的是保持其在整个 JVM 进程中是唯一的，
    //并且 ActiveDaemonThread 会在此启动
    private final static  ActiveMessageQueue activeMessageQueue=new ActiveMessageQueue();

    //不允许外部 new
    private OrderServiceFactory(){}

    //创建 OrderServiceProxy
    public static OrderService toActiveObject(OrderService orderService){
        return new OrderServiceProxy(orderService,activeMessageQueue);
    }

}
