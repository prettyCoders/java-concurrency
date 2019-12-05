package chapter27.general_active_object;


import chapter19.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ActiveServiceFactory 是通用 Active Object 的核心类，其负责生成 Service 的代理以及构建 ActiveMessage
 */
public class ActiveServiceFactory {

    //定义 ActiveMessageQueue，用于存放 ActiveMessage
    private final static ActiveMessageQueue QUEUE = new ActiveMessageQueue();

    //active 方法根据 Active Service 实例生成一个动态代理实例
    public static <T> T active(T instance) {
        //生成 Service 的代理类
        Object proxy = Proxy.newProxyInstance(
                instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(),
                new ActiveInvocationHandler<>(instance)
        );
        return (T) proxy;
    }

    //ActiveInvocationHandler 是 InvocationHandler 的子类，生成 Proxy 时需要使用到
    private static class ActiveInvocationHandler<T> implements InvocationHandler {

        private final T instance;

        ActiveInvocationHandler(T instance) {
            this.instance = instance;
        }

        //这个方法会在代理对象调用方法的时候调用，而不是去调用原对象的方法
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果接口被 @ActiveMethod 标记，则会转换为 ActiveMessage
            if (method.isAnnotationPresent(ActiveMethod.class)) {
                //检查该方法是否符合规范
                this.checkMethod(method);
                ActiveMessage.Builder builder = new ActiveMessage.Builder();
                builder.useMethod(method).withObject(args).forService(instance);
                Object result = null;
                if (this.isReturnFutureType(method)) {
                    result = new ActiveFuture<>();
                    builder.returnFuture((ActiveFuture) result);
                }
                //将 ActiveMessage 加入至队列中
                QUEUE.offer(builder.build());
                return result;
            } else {
                //如果是普通方法（没有被 @ActiveMethod 标记），则会正常执行
                return method.invoke(instance, args);
            }
        }


        //检查有返回值的方法是否为 Future,否则将会抛出 IllegalActiveMethodException 异常
        private void checkMethod(Method method) throws IllegalActiveMethodException {
            //有返回值，必须是 ActiveMFuture 类型的返回值
            if (!isReturnVoidType(method) && !isReturnFutureType(method)) {
                throw new IllegalActiveMethodException("the method [ "+method.getName()+" ] return type must be void/Future");
            }
        }

        //判断方法是否为 Future 返回类型
        private boolean isReturnFutureType(Method method) {
            return method.getReturnType().isAssignableFrom(Future.class);
        }

        //判断方法是否无返回类型
        private boolean isReturnVoidType(Method method) {
            return method.getReturnType().equals(Void.TYPE);
        }



    }

}
