package chapter28;

/**
 * EventBus 会将方法的调用交给 Runnable 接口去执行，由于 Runnable 接口不能抛出 checked 异常信息，
 * 并且在每一个 Subscribe 方法中，也不允许将异常抛出从而影响 EventBus 对后续 Subscriber 进行消息推送，
 * 但是异常信息又不能被忽略掉，因此注册一个异常回调接口就可以知道在进行消息广播推送时都发生了什么。
 */
public interface EventExceptionHandler {
    void handle(Throwable cause, EventContext context);
}
