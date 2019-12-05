package chapter28;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * AsyncEventBus 异步 EventBus,继承自同步 EventBus，然后将 Thread-Per-Message 用异步处理任务的 Executor
 * 替换掉 EventBus 中的同步 Executor 即可
 */
public class AsyncEventBus extends EventBus {

    public AsyncEventBus(String busName, EventExceptionHandler exceptionHandler, Executor executor) {
        super(busName, exceptionHandler, executor);
    }

    public AsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName,null,executor);
    }

    public AsyncEventBus(ThreadPoolExecutor executor){
        this("default-async-bus",null,executor);
    }

    public AsyncEventBus(EventExceptionHandler exceptionHandler, Executor executor) {
        super("default-async-bus", exceptionHandler, executor);
    }

}
