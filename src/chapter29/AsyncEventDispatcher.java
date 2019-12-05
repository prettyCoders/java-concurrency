package chapter29;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AsyncEventDispatcher 负责以并发的方式 dispatch Message,其中 Event 对应的 Channel 只能是 AsyncChannel 类型，
 * 并且也对外暴露了 shutdown 方法
 */
public class AsyncEventDispatcher implements DynamicRouter<Event> {

    /**
     * 使用线程安全的 ConcurrentHashMap
     */
    private final Map<Class<? extends Event>, AsyncChannel> routerTable;

    public AsyncEventDispatcher() {
        this.routerTable = new ConcurrentHashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Event> messageType, Channel<? extends Event> channel) {
        if (!(channel instanceof AsyncChannel)) {
            throw new IllegalArgumentException("The channel must be AsyncChannel type.");
        }
        this.routerTable.put(messageType, (AsyncChannel) channel);
    }

    @Override
    public void dispatch(Event message) {
        if (routerTable.containsKey(message.getType())) {
            //直接获取对应的 Channel 处理 Message
            routerTable.get(message.getType()).dispatch(message);
        } else {
            throw new MessageMatcherException("Can't match the channel for [ " + message.getType() + " ] type");
        }
    }

    public void shutdown() {
        //关闭所有的 Channel 以释放资源
        routerTable.values().forEach(AsyncChannel::stop);
    }
}
