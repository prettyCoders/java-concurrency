package chapter03;

/**
 * 线程休眠，不会放弃monitor(线程同步和锁相关)的所有权
 * JDK1.5之后推荐使用TimeUnit
 */
public class ThreadSleep {

    public static void main(String[] args) {

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            sleep(2000L);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Total spent %d ms", (endTime - startTime)));
        }).start();

        long startTime = System.currentTimeMillis();
        sleep(3000L);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Main thread total spent %d ms", (endTime - startTime)));
    }


    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

