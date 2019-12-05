package chapter28;

import java.lang.reflect.Method;

/**
 *Subscriber 类封装了对象实例和被 @Subscribe 标记的方法，也就是说一个对象实例有可能会被封装成若干个 Subscriber
 */
public class Subscriber {

    private final Object subscriberObject;

    private final Method subscriberMethod;

    private boolean disable=false;

    public Subscriber(Object subscriberObject, Method subscriberMethod) {
        this.subscriberObject = subscriberObject;
        this.subscriberMethod = subscriberMethod;
    }

    public Method getSubscribeMethod() {
        return subscriberMethod;
    }

    public Object getSubscribeObject() {
        return subscriberObject;
    }

    public boolean isDisable(){
        return disable;
    }

    public void setDisable(boolean disable){
        this.disable=disable;
    }

}
