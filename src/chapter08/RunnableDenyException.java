package chapter08;

/**
 * RunnableDenyException 是 RuntimeException 的子类，主要用于通知任务提交者，任务队列已无法再接收新的任务。
 */
public class RunnableDenyException extends RuntimeException {
    //重写RuntimeException的构造方法即可
    public RunnableDenyException(String message) {
        super(message);
    }
}
