package chapter29;

import java.util.HashMap;
import java.util.Map;

/**
 * EventDispatcher 是对 DynamicRouter 的一个最基本实现，适合在单线程的情况下使用，
 * 因此不需要考虑线程安全问题
 */
public class EventDispatcher implements DynamicRouter<Message> {

    /**
     * 用于保存 Channel 和 Message 之间的关系
     */
    private final Map<Class<? extends Message>, Channel> routerTable;

    public EventDispatcher(){
        //初始化 routerTable，但是在该实现中用 HashMap 作为路由表
        routerTable=new HashMap<>();
    }



    @Override
    public void registerChannel(Class<? extends Message> messageType, Channel<? extends Message> channel) {
        this.routerTable.put(messageType,channel);
    }

    @Override
    public void dispatch(Message message) {
        if(routerTable.containsKey(message.getType())){
            //直接获取对应的 Channel 处理 Message
            routerTable.get(message.getType()).dispatch(message);
        }else {
            throw new MessageMatcherException("Can't match the channel for [ "+message.getType()+" ] type");
        }
    }
}
