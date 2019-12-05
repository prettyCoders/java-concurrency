package chapter27.general_active_object;

import chapter19.Future;

import java.lang.reflect.Method;

/**
 * 包可见，ActiveMessage 只在框架内部使用，不会对外暴露
 */
class ActiveMessage {

    //接口方法参数
    private final Object[] objects;

    //接口方法
    private final Method method;

    //有返回值的方法，会返回 ActiveFuture<?> 类型
    private final ActiveFuture<Object> future;

    //具体的 Service 接口
    private final Object service;

    /**
     * 构造 ActiveMessage 必须使用 Builder 方式进行 build ,其中包含调用某个方法必须的入参（objects）、
     * 代表该方法的 java.lang.reflect.Method 实例（method）、将要执行的 ActiveService 实例(service)、
     *
     * @param builder
     */
    private ActiveMessage(Builder builder) {
        this.objects = builder.objects;
        this.method = builder.method;
        this.future = builder.future;
        this.service = builder.service;
    }

    //通过反射的方式执行调用执行的具体实现
    public void execute() {
        try {
            //执行接口方法
            Object result = method.invoke(service, objects);
            if (future != null) {
                //如果有返回值的接口方法，则需要通过 get 方法获得最终的结果
                Future<?> realFuture = (Future<?>) result;
                Object realResult = realFuture.get();
                //将结果交给 ActiveFuture，接口方法的线程会得到返回
                future.finish(realResult);
            }
        } catch (Exception e) {
            //如果发生异常，那么有返回值的方法将会显示地指定结果为 null,无返回值的接口则会忽略该异常
            if (future != null) {
                future.finish(null);
            }
        }
    }

    //Builder 主要负责对 ActiveMessage 的构建，是一种典型的 Gof Builder 设计模式
    static class Builder{

        private Object[] objects;

        private Method method;

        private ActiveFuture<Object> future;

        private Object service;

        public Builder useMethod(Method method){
            this.method=method;
            return this;
        }

        public Builder returnFuture(ActiveFuture<Object> future){
            this.future=future;
            return this;
        }

        public Builder withObject(Object[] objects){
            this.objects=objects;
            return this;
        }

        public Builder forService(Object service){
            this.service=service;
            return this;
        }

        public ActiveMessage build(){
            return new ActiveMessage(this);
        }

    }
}
