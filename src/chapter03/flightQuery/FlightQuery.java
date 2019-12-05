package chapter03.flightQuery;

import java.util.List;

/**
 * 串行任务局部并行化处理场景：
 * 用户再APP输出出发地和目的地，服务器接收到请求，到各大航空公司接口查询数据，
 * 数据经过加工后返回，其中查询这个步骤使用多线程并行处理，然后对数据统一整理，节约时间
 *
 * 航班查询接口
 */
public interface FlightQuery {
    //如果想得到某个线程的运行结果，就需要自己定义一个有返回值的接口
    List<String> get();
}
