package chapter01;


/**
 * 模拟叫号大厅叫号的过程(继承Thread)
 * 注意：共享变量index存在线程安全问题
 * TicketWindow代表大厅里的出号机器
 */
public class TicketWindow extends Thread {
    //柜台名称
    private final String name;

    //最多受理50笔业务
    private static final int Max = 50;

    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (index <= Max) {
            System.out.println("柜台" + name + "当前的号码是：" + (index++));
        }
    }



    public static void main(String[] args){
        TicketWindow t1=new TicketWindow("一号出号机");
        t1.start();

        TicketWindow t2=new TicketWindow("二号出号机");
        t2.start();

        TicketWindow t3=new TicketWindow("三号出号机");
        t3.start();

        TicketWindow t4=new TicketWindow("四号出号机");
        t4.start();
    }
}
