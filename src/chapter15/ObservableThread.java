package chapter15;

/**
 * ObservableThread 是任务监控的关键，继承自 Thread 类和 Observable 接口，
 * 并且在构造期间需要传入Task的具体实现
 */
public class ObservableThread<T> extends Thread implements Observable {

    private final TaskLifecycle<T> lifecycle;

    private final Task<T> task;

    private Cycle cycle;

    //指定Task的实现，默认情况下使用EmptyLifeCycle
    public ObservableThread(Task<T> task) {
        this(new TaskLifecycle.EmptyLifeCycle<>(), task);
    }


    public ObservableThread(TaskLifecycle<T> lifecycle, Task<T> task) {
        super();
        //Task不允许为null
        if (task == null) {
            throw new IllegalArgumentException("The task is required.");
        }
        this.lifecycle = lifecycle;
        this.task = task;
    }

    /**
     * 重写父类的run()，并将其修饰为final类型，不允许子类再次对其进行重写，run()在线程的运行期间，
     * 可监控任务在执行过程中的各个生命周期阶段，任务每经过一个阶段，相当于发生了一次事件。
     */
    @Override
    public final void run() {
        //在执行逻辑单元的时候分别触发相应的事件
        this.update(Cycle.START,null,null);
        try {
            this.update(Cycle.RUNNING,null,null);
            T result=this.task.call();
            this.update(Cycle.DONE,result,null);
        }catch (Exception e){
            this.update(Cycle.ERROR,null,e);
        }
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle=cycle;
        if(lifecycle==null){
            return;
        }

        try {
            switch (cycle){
                case START:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(),result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(),e);
                    break;
            }
        }catch (Exception ex){
            if(cycle==Cycle.ERROR){
                throw ex;
            }
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }
}
