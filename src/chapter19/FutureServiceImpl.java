package chapter19;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FutureServiceImpl 的作用主要在于提交任务的时候创建一个新的线程来处理这个任务，
 * 进而达到任务异步执行的效果
 *
 * @param <IN>
 * @param <OUT>
 */
public class FutureServiceImpl<IN, OUT> implements FutureService<IN, OUT> {

    //为执行的线程指定名字前缀
    private final static String FUTURE_THREAD_PREFIX = "FUTURE-";

    private AtomicInteger nextCounter = new AtomicInteger(0);

    private String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }


    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        new Thread(() -> {
            runnable.run();
            //任务执行结束后将null作为结果传给future
            future.finish(null);
        }, getNextName()).start();
        return future;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input, CallBack<OUT> callBack) {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() -> {
            OUT result = task.get(input);
            //任务执行结束后将 result 作为结果传给 future
            future.finish(result);
            //执行回调接口
            if (null != result && callBack != null) {
                callBack.call(result);
            }
        }, getNextName()).start();
        return future;
    }
}
