package chapter03;

/**
 * 线程优先级，具有不确定性
 */
public class ThreadPriority {
    public static void main(String[] args){
        Thread t1=new Thread(()->{
            while (true){
                System.out.println("=============T1");
            }
        });

        Thread t2=new Thread(()->{
            while (true){
                System.out.println("T2");
            }
        });

        t1.setPriority(3);
        t2.setPriority(10);
        t1.start();
        t2.start();
    }


}
