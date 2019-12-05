package chapter28;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 注册表维护了 topic 和 subscriber 之间的关系，当有 Event 被 post 之后，
 * Dispatcher 需要知道该消息应该发送给哪个 Subscriber 的实例和对应的方法，
 * Subscriber 没有任何特殊的要求，就是普通的类，不需要继承任何父类或者实现
 * 任何接口
 * 由于 Registry 是在 Bus 中使用，不能暴露给外部，所以设计成了包可见的类，
 * 所以 EventBus 对 Subscriber 没有做任何限制，但是要接受 event 的回调则需要将
 * 方法使用注解 @Subscribe 进行标记（可指定 topic），通一个 Subscriber 的不同方法
 * 通过 @Subscribe 注解之后可以接受来之两个不同 topic 的消息
 */
class Registry {
    /**
     * 存储 Subscriber 集合和 topic 之间关系的 map
     */
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        //获取 Subscriber Object 的方法集合然后进行绑定
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    public void unbind(Object subscriber) {
        //为了提高速度，只对 Subscriber 进行失效操作
        subscriberContainer.forEach((key, queue) -> queue.forEach(s -> {
            if (s.getSubscribeObject() == subscriber) {
                s.setDisable(true);
            }
        }));
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);
    }

    private void tierSubscriber(Object subscriber, Method method) {
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        //当某 topic 没有 Subscriber Queue 的时候创建一个
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        //创建一个 Subscriber 并加入 Subscriber 列表中
        subscriberContainer.get(topic).add(new Subscriber(subscriber, method));
    }

    private List<Method> getSubscribeMethods(Object subscriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        //不断获取当前类和父类的所有 @Subscribe 方法
        while (temp != null) {
            //获取所有方法
            Method[] declaredMethods = temp.getDeclaredMethods();
            //只有 public 方法 && 有一个入参 && 最重要的是被 @Subscribe 标记的方法才符合回调方法
            Arrays.stream(declaredMethods).filter(m ->
                    m.isAnnotationPresent(Subscribe.class) &&
                            m.getParameterCount() == 1 &&
                            m.getModifiers() == Modifier.PUBLIC
            ).forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }


}
