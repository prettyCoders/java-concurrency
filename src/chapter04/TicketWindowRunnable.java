package chapter04;


/**
 * 模拟叫号大厅叫号的过程(实现Runnable)
 * TicketWindow代表大厅里的出号机器
 */
public class TicketWindowRunnable implements Runnable {

    //最多受理50笔业务
    private static final int Max = 50;

    private int index = 1;

    private final Object MUTEX = new Object();

    @Override
    public void run() {
        synchronized (MUTEX) {
            while (index <= Max) {
                System.out.println(Thread.currentThread() + "当前的号码是：" + (index++));
            }
        }
    }


    public static void main(String[] args) {
        final TicketWindowRunnable runnable = new TicketWindowRunnable();
        Thread t1 = new Thread(runnable, "一号窗口 ");
        Thread t2 = new Thread(runnable, "二号窗口 ");
        Thread t3 = new Thread(runnable, "三号窗口 ");
        Thread t4 = new Thread(runnable, "四号窗口 ");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
