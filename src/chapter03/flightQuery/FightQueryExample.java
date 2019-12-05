package chapter03.flightQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询示例
 */
public class FightQueryExample {
    //航空公司
    private static List<String> fightCompany = Arrays.asList("CSA", "CEA", "HNA");

    public static void main(String[] args) {
        //查询各大航空公司从上海到北京的航班信息
        List<String> results = search("SH", "BJ");
        System.out.println("======result======");
        results.forEach(System.out::println);
    }

    private static List<String> search(String origin, String destination) {
        final List<String> result = new ArrayList<>();
        //创建查询航班信息的的线程列表
        List<FlightQueryTask> tasks=fightCompany.stream().map(company->createSearchTask(company,origin,destination)).collect(Collectors.toList());
        //启动这几个线程
        tasks.forEach(Thread::start);
        //分别调用这几个线程的join方法，阻塞当前线程
        tasks.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //在此之前，当前线程会阻塞住，获取每个查询线程的结果，并且加入到result中
        tasks.stream().map(FlightQuery::get).forEach(result::addAll);
        return result;
    }

    //创建查询任务
    private static FlightQueryTask createSearchTask(String company, String origin, String destination) {
        return new FlightQueryTask(company,origin,destination);
    }
}
