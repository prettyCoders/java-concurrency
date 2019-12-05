package chapter15;

/**
 * 任务生命周期会触发的接口
 */
public interface TaskLifecycle<T> {

    //任务启动时触发
    void onStart(Thread thread);

    //任务正在运行时触发
    void onRunning(Thread thread);

    //任务结束时触发，并返回任务执行结束后的结果，允许null
    void onFinish(Thread thread,T result);

    //任务执行报错时触发
    void onError(Thread thread,Exception e);


    /**
     * 生命周期接口的空实现（Adapter），主要是为了使用这保持对Thread类的使用习惯
     * @param <T>
     */
    class EmptyLifeCycle<T> implements TaskLifecycle<T>{

        @Override
        public void onStart(Thread thread) {

        }

        @Override
        public void onRunning(Thread thread) {

        }

        @Override
        public void onFinish(Thread thread, T result) {

        }

        @Override
        public void onError(Thread thread, Exception e) {

        }
    }
}
