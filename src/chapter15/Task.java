package chapter15;

/**
 * 函数接口
 * 由于我们需要对线程中的任务执行增加可观察能力，并且需要获得最后的计算结果，
 * 因此 Runnable 接口在可观察的线程中将不再使用，取而代之的是本接口，作用于Runnable类似，
 * 主要用于承载任务的逻辑执行单元
 * @param <T>
 */
@FunctionalInterface
public interface Task<T> {

    //任务执行接口，该接口允许有返回值
    T call();

}
