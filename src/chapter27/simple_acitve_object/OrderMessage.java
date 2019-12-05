package chapter27.simple_acitve_object;

import java.util.Map;

/**
 * OrderMessage 的主要处理 order 方法，从 param 中获取接口参数，然后执行真正的 orderService 的 order 方法
 */
public class OrderMessage extends MethodMessage {

    public OrderMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        //获取参数
        String account= (String) params.get("account");
        long orderId= (long) params.get("orderId");
        //执行真正的 order 方法
        orderService.order(account,orderId);
    }
}
