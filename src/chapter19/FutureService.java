package chapter19;

/**
 * FutureService 主要用于提交任务，提交的任务主要有两种，
 * 第一种不要返回值，第二种需要获得最终的计算结果。
 * 接口中提供了对 FutureServiceImpl 构建的工厂方法
 */
public interface FutureService<IN, OUT> {

    //提交不需要返回值的任务，Future.get() 返回null
    Future<?> submit(Runnable runnable);

    //提交需要返回值的任务，其中 Task 接口代替 Runnable
    Future<OUT> submit(Task<IN, OUT> task, IN input, CallBack<OUT> callBack);

    //使用静态方法创建一个 FutureService 的实现
    static <T, R> FutureService<T, R> newService() {
        return new FutureServiceImpl<>();
    }

}
