package chapter15;

/**
 *观察者接口
 * 该接口主要是暴露给调用者使用
 */
public interface Observable {

    //任务生命周期
    enum Cycle {
        START, RUNNING, DONE, ERROR
    }

    //获取当前任务的生命周期状态
    Cycle getCycle();

    //定义线程的启动方法，主要作用是为了屏蔽Thread的其他方法
    void start();

    //定义线程的打断方法，理由同上
    void interrupt();
}
