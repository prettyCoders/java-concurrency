package chapter01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库查询，策略模式
 */
public class RecordQuery {

    private Connection connection;

    public RecordQuery(Connection connection) {
        this.connection = connection;
    }

    /**
     * query()只负责将数据查询出来，然后调用RowHandler进行封装，至于封装成什么样，取决去参数handler
     * @param handler RowHandler的某个实现类
     * @param sql sql语句
     * @param params sql语句参数
     * @param <T> RowHandler的实现类指定的返回数据类型
     * @return 封装后的数据
     * @throws SQLException SQL异常
     */
    public <T> T query(RowHandler<T> handler, String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            ResultSet resultSet = statement.executeQuery();
            return handler.handle(resultSet);
        }
    }
}
