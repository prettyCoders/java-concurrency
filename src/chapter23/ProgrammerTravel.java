package chapter23;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 程序员们旅行
 */
public class ProgrammerTravel extends Thread{

    //门阀
    private final Latch latch;

    //程序员
    private final String programer;

    //交通工具
    private final String transportation;

    public ProgrammerTravel(Latch latch, String programmer, String transportation) {
        this.latch = latch;
        this.programer = programmer;
        this.transportation = transportation;
        this.setName(programmer);
    }

    @Override
    public void run() {
        try {
            //乘坐交通工具所花费的时间
            long time=ThreadLocalRandom.current().nextInt(10);
            System.out.println(programer+" start take the transportation "+transportation+" maybe spent "+time+" ms.");
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(programer+" arrived by "+transportation);
        latch.countDown();
    }
}
