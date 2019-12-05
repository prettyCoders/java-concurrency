package chapter27.simple_acitve_object;

import chapter19.Future;

import java.util.Map;

public class FindOrderDetailsMessage extends MethodMessage {

    public FindOrderDetailsMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        Future<String> realResult = orderService.findOrderDetails((long) params.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");
        try {
            //该方法会导致阻塞，直到 findOrderDetails 方法完全执行结束
            String result = realResult.get();
            //当 findOrderDetails 方法完全执行结束后，将结果通过 finish 方法传递给 activeFuture
            activeFuture.finish(result);
        } catch (InterruptedException e) {
            activeFuture.finish(null);
        }
    }
}
