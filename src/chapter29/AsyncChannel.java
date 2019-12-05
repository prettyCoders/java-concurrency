package chapter29;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 定义 AsyncChannel 作为基类，该类中提供了 Message 的并发处理能力
 */
public abstract class AsyncChannel implements Channel<Event> {

    /**
     * 使用 ExecutorService 多线程的方式提交给 Message
     */
    private final ExecutorService executorService;

    /**
     * 默认构造函数，提供 CPU 核心数量 x2 的线程数量
     */
    public AsyncChannel() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

    public AsyncChannel(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * 重写 dispatch 方法，并且用 final 修饰，避免子类重写
     *
     * @param message 消息
     */
    @Override
    public final void dispatch(Event message) {
        executorService.submit(() -> this.handle(message));
    }

    /**
     * 提供抽象方法，供子类实现具体细节的 Message 处理
     */
    protected abstract void handle(Event message);

    /**
     * 提供关闭 ExecutorService 的方法
     */
    void stop() {
        if (null != executorService && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
