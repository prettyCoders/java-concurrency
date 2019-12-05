package chapter28;

import java.lang.reflect.Method;

/**
 * EventContext 接口提供了获取消息源、消息体、以及该消息是由哪一个 Subscriber 的哪个 subscribe 方法所接受，
 * 主要用于消息推送出错被回调接口 EventExceptionHandler 使用
 */
public interface EventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribeMethod();

    Object getEvent();

}
