package chapter27.simple_acitve_object;

import chapter19.Future;

import java.util.HashMap;
import java.util.Map;

/**
 * OrderServiceProxy 是 OrderService 的子类，它的作用是将 OrderService 的每一个方法封装成 MethodMessage
 * 然后提交给 ActiveMessage 队列，在使用 OrderService 接口方法的时候，其实就是在调用 OrderServiceProxy 中的方法
 */
public class OrderServiceProxy implements OrderService {

    private final OrderService orderService;
    private final ActiveMessageQueue activeMessageQueue;

    public OrderServiceProxy(OrderService orderService, ActiveMessageQueue activeMessageQueue) {
        this.orderService = orderService;
        this.activeMessageQueue = activeMessageQueue;
    }

    @Override
    public Future<String> findOrderDetails(long orderId) {
        //定义一个ActiveFuture ，并且可支持立即返回
        final ActiveFuture<String> activeFuture=new ActiveFuture<>();
        //收集方法入参以及返回的 ActiveFuture 封装成 MethodMessage
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",orderId);
        params.put("activeFuture",activeFuture);
        MethodMessage message=new FindOrderDetailsMessage(params,orderService);
        //将 MethodMessage 保存至 activeMessageQueue 中
        activeMessageQueue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        //收集方法入参并 封装成 MethodMessage，然后 offer 至队列中
        Map<String,Object> params=new HashMap<>();
        params.put("account",account);
        params.put("orderId",orderId);
        MethodMessage message=new OrderMessage(params,orderService);
        activeMessageQueue.offer(message);
    }
}
