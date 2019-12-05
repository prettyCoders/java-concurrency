package chapter23;

import java.util.concurrent.TimeUnit;

public class ProgrammerTravelTest {

    public static void main(String[] args) throws InterruptedException {
        //定义 Latch ,limit 设置为4
        final Latch latch=new CountDownLatch(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("All of programmer arrived.");
            }
        });
        new ProgrammerTravel(latch,"a","Bus").start();
        new ProgrammerTravel(latch,"b","Walking").start();
        new ProgrammerTravel(latch,"c","Subway").start();
        new ProgrammerTravel(latch,"d","Bicycle").start();
        //main 线程会阻塞，直到所有程序员到达目的地
        try {
            //不是很准确，超时时间会超过指定的时间1到两秒
            latch.await(TimeUnit.SECONDS,5);
        } catch (WaitTimeOutException e) {
            e.printStackTrace();
        }
    }
}
