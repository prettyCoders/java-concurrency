package chapter19;

/**
 * Future 提供了获取计算结果和判断任务是否完成的两个接口，其中获取计算结果将会导致调用阻塞（在任务还未完成的情况下）
 */
public interface Future<T> {

    //返回计算结果，会导致调用阻塞（在任务还未完成的情况下）
    T get() throws InterruptedException;

    //判断任务是否完成
    boolean done();

}
