package chapter08;

/**
 * DenyPolicy 主要用于当queue中的Runnable达到上限时，决定采用何种策略通知提交者，
 * 该接口定义了三种默认实现
 */
@FunctionalInterface
public interface DenyPolicy {

    //拒绝接口
    void reject(Runnable runnable,ThreadPool threadPool);

    //该拒绝策略会直接将任务丢弃
    class DiscardDenyPolicy implements DenyPolicy{

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            //do noting
        }
    }

    //该策略会向提交者抛出异常
    class AbortDenyPolicy implements DenyPolicy{

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            throw new RunnableDenyException("The Runnable "+runnable+" will be abort.");
        }
    }

    //该拒绝策略会试任务在提交者所在的线程中执行任务，而不会加入到线程池中
    class RunnerDenyPolicy implements DenyPolicy{

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            if(!threadPool.isShutdown()){
                runnable.run();
            }
        }
    }
}
