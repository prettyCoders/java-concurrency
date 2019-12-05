package chapter29;

/**
 * Channels 是一种比较重要的概念，Channel 主要用于接受来自 Event Loop 分配的消息，
 * 每一个 Channel 负责处理一种类型的消息
 * @param <E>
 */
public interface Channel<E extends Message> {

    /**
     * dispatch 负责 Message 的调度
     * @param message 消息
     */
    void dispatch(E message);

}
