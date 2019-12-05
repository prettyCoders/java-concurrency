package chapter29;

/**
 * EAD框架
 * Message 是对 Event 更高一个层级的抽象，每一个 Message 都有一个特定的 Type 用于与对应的 Handler 做关联
 */
public interface Message {

    /**
     * 返回 Message 类型
     */
    Class<? extends Message> getType();



}
