package chapter27.simple_acitve_object;

import chapter19.Future;

public interface OrderService {

    /**
     * 根据订单编号查询订单明细，有入参也有返回值，但是返回类型必须是Future
     * 因为方法的执行是在另一个线程中进行的，势必不会立即得到正确的结果，通过Future
     * 可以立即得到返回
     */
    Future<String> findOrderDetails(long orderId);

    //提交订单，但是没有返回值
    void order(String account, long orderId);
}
