package chapter24.thread_per_message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Operator {
    public final ExecutorService executorService=Executors.newFixedThreadPool(10);

    public void call(String business) {
        //使用线程池为每一个请求创建一个线程去处理
        TaskHandler taskHandler = new TaskHandler(new Request(business));
        executorService.execute(taskHandler);
    }

}
