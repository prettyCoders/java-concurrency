package chapter02;

/**
 * 守护线程(后台线程)
 * 该demo永远不会结束运行，因为JVM中一直存在一个非守护线程在运行
 * 应用场景：
 * 比如某个游戏程序，其中一个线程正在与服务器不断得交互以获取玩家最近的金币、武器等信息，
 * 若希望在客户端退出的时候能自动结束这种死循环线程，那么就可以将此线程设置为守护线程
 */
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread thread= new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //守护线程需在线程启动之前设置
//        thread.setDaemon(true); //设置线程为守护线程
        thread.start();
        Thread.sleep(2000L);
        System.out.println("Main thread finished lifecycle");
    }
}
