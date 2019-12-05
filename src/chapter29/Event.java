package chapter29;

/**
 * Event 是对 Message 的一个最简单的实现，在以后的使用中，将 Event 直接作为其他 Message 的基类即可，
 * 这种做法类似适配器模式
 */
public class Event implements Message {
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
