package chapter03.flightQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 查询航班信息的task就是一个Thread的子类，主要用于到各大航空公司获取数据
 */
public class FlightQueryTask extends Thread implements FlightQuery {

    //出发地
    private final String origin;
    //目的地
    private final String destination;
    //航班数据列表，包含所有航空公司的数据，查询线程查询出结果后会添加到这个集合里
    private final List<String> fightList = new ArrayList<>();

    public FlightQueryTask(String company, String origin, String destination) {
        //用航空公司名代替线程名
        super("[" + company + "]");
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void run() {
        System.out.printf("%s-query from %s to %s\n", getName(), origin, destination);

        //随机数，用于模拟航空公司接口查询响应时间
        int randomVal=ThreadLocalRandom.current().nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(randomVal);
            //模拟数据：航空公司名-随机数
            this.fightList.add(getName()+"-"+randomVal);
            System.out.printf("The fight:%s list query successful\n",getName());
        } catch (InterruptedException e) {
        }
    }

    //返回查询结果
    @Override
    public List<String> get() {
        return this.fightList;
    }
}
