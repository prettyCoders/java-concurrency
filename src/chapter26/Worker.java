package chapter26;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Worker 流水线工人，是 Thread 的子类，不断得从流水线上提取产品然后进行再次加工，
 * 加工的方法是 create() (对应该产品的加工说明书)
 */
public class Worker extends Thread {

    private final ProductionChannel channel;
    //主要用于获取一个随机值，模拟加工一个产品需要耗费的时间，当然，
    //每个工人操作时耗费的时间也不一样
    private final static Random random = new Random(System.currentTimeMillis());

    public Worker(String workerName, ProductionChannel channel) {
        super(workerName);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //从传送带上获取产品
                Production production = channel.takeProduction();
                System.out.println(getName() + " process the " + production);
                //对产品进行加工
                production.create();
                TimeUnit.SECONDS.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
