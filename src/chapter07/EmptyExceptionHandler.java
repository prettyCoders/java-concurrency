package chapter07;

import java.util.concurrent.TimeUnit;

/**
 * 如果当前线程运行期间发生异常
 * 1、如果该group有父group的话，则直接调用父group的uncaughtException方法
 * 2、如果设置了全局默认的UncaughtExceptionHandler。则调用uncaughtException方法
 * 3、如果上面两个都没有，则会直接将异常的堆栈信息直接定位到System.err中
 */
public class EmptyExceptionHandler {

    public static void main(String[] args) {
        ThreadGroup mainGroup=Thread.currentThread().getThreadGroup();
        System.out.println(mainGroup.getName());
        System.out.println(mainGroup.getParent());
        System.out.println(mainGroup.getParent().getParent());

        final Thread thread=new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {

            }
            //这里会出现unchecked异常
            System.out.println(1/0);
        },"Test-Thread");
        thread.start();
    }

}
