package chapter29;

/**
 * DynamicRouter 的作用是帮助 Event 找到合适的 Channel 并且传送给它
 */
public interface DynamicRouter<E extends Message> {

    /**
     * 针对每一种 Message 类型注册相关的 Channel，只有找到合适的 Channel，该 Message 才会被处理
     */
    void registerChannel(Class<? extends E> messageType, Channel<? extends E> channel);

    /**
     * 为相应的 Channel 分配 Message
     */
    void dispatch(E message);

}
