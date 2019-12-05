package chapter27.general_active_object;

import chapter19.Future;

import static java.lang.Thread.currentThread;

public class ActiveObjectTest {


    public static void main(String[] args) throws InterruptedException {

        //运行测试代码会立即得到返回，10秒之后，order方法执行结束，调用方法的线程是主线程，
        //但是执行该方法的线程却是其他线程(ActiveDaemonThread)，这也正是 Active Object 可接受异步消息的意思

        OrderService orderService = ActiveServiceFactory.active(new OrderServiceImpl());

//        Future<String> future = orderService.findOrderDetails(123456);
//        System.out.println("Return immediately");
//        System.out.println(future.get());

//        orderService.order("hello", 123456);
//        System.out.println("Return immediately");

        TestService testService = ActiveServiceFactory.active(new TestServiceImpl());
        testService.test("assssssss", 789456);
        System.out.println("Return immediately");

        currentThread().join();
    }

}
