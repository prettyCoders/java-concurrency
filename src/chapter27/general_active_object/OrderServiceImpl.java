package chapter27.general_active_object;

import chapter19.Future;
import chapter19.FutureService;

import java.util.concurrent.TimeUnit;

/**
 * OrderServiceImpl 是 OrderService 的具体实现，该类是在执行过程中将要被使用的类
 */
public class OrderServiceImpl implements OrderService {

    @ActiveMethod
    @Override
    public Future<String> findOrderDetails(long orderId) {
        return FutureService.<Long,String>newService().submit(input -> {
            try {
                //模拟耗时
                TimeUnit.SECONDS.sleep(2);
                System.out.println("process the orderID->"+orderId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "the order details information.";
        },orderId,null);
    }

    @ActiveMethod
    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("process the order account "+account+",orderID "+orderId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
