package chapter28;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注册对象给 EventBus 的时候需要指定接收消息时的回调方法，这里采用注解的方式进行 Event 回调
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {

    /**
     * 注解可指定 topic,不指定的情况下为默认的 topic(default-topic)
     * @return 主题名
     */
    String topic() default "default-topic";
}
