package chapter27.simple_acitve_object;

class ActiveDaemonThread extends Thread{

    private final ActiveMessageQueue queue;

    public ActiveDaemonThread(ActiveMessageQueue queue) {
        super("ActiveDaemonThread");
        this.queue = queue;
        //设置为守护线程
        this.setDaemon(true);
    }

    @Override
    public void run() {
        for (;;){
            //从 MethodMessage 队列中获取一个 MethodMessage，然后执行 execute 方法
            MethodMessage methodMessage=this.queue.take();
            methodMessage.execute();
        }
    }
}
