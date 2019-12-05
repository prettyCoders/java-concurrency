package chapter03;

import java.util.concurrent.TimeUnit;

/**
 * volatile关键字
 * <p>
 * 由于线程的interrupt标识很可能被擦除，或者逻辑单元中不会使用任何可中断方法，
 * 所以使用volatile关键字修饰的开关flag关闭线程
 */
public class FlagThreadExit {
    public static void main(String[] args) throws InterruptedException {
        MyTask myTask=new MyTask();
        myTask.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("System will be shutdown.");
        myTask.close();
    }

    static class MyTask extends Thread {
        private volatile boolean closed = false;

        @Override
        public void run() {
            System.out.println("I will be work.");
            //如果没主动关闭并且没被打断就继续执行任务，反之关闭线程
            while (!closed && !isInterrupted()) {
                //working
            }
            System.out.println("I will be exiting.");
        }

        private void close(){
            this.closed=true;
            this.interrupt();
        }
    }
}
