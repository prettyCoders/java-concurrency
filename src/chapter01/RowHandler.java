package chapter01;

import java.sql.ResultSet;

/**
 * 策略模式的应用
 *将JDBC查询之后的数据封装部分抽取成一个策略接口
 * 此接口只负责对数据库中查询出来的结果集进行操作，具体最终返回什么样的数据接口，需要自己去实现，类似于Runnable接口
 * @param <T>
 */
public interface RowHandler<T> {
    T handle(ResultSet rs);
}
