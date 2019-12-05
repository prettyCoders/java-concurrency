package chapter28;

/**
 * Bus 定义了 EventBus 的所有使用方法
 */
public interface Bus {

    /**
     * 将某个对象注册到 Bus 上，从此以后该类就成为 Subscriber 了
     * @param subscriber 订阅者
     */
    void register(Object subscriber);

    /**
     * 取消注册，不会再接收到来自 Bus 的任何消息
     * @param subscriber 订阅者
     */
    void unregister(Object subscriber);

    /**
     * 提交 Event 到默认的 topic
     * @param event 事件
     */
    void post(Object event);

    /**
     * 提交 Event 到指定的 topic
     * @param event 事件
     * @param topic 主题
     */
    void post(Object event,String topic);

    /**
     * 关闭 Bus
     */
    void close();

    /**
     * 返回 Bus 的名称标识
     * @return 名称标识
     */
    String getBusName();

}
