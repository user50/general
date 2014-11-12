package general.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseManager {

    private ConnectionPool connectionPool;

    public DatabaseManager(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public <T> List<T> executeQueryForList(Query<T> query) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String rawSql = query.getRawSql();
            preparedStatement = connection.prepareStatement(rawSql);
            query.prepare(preparedStatement);

            resultSet = preparedStatement.executeQuery();

            List<T> list = new ArrayList<T>();

            while (resultSet.next()){
                list.add(query.fill(resultSet));
            }

            return list;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            connectionPool.releaseConnection(connection);
        }
    }

    public <T> T executeQueryForOne(Query<T> query) throws SQLException {
        List<T> list = executeQueryForList(query);

        if (list.size() > 1)
            throw new RuntimeException("Ambiguity during executing query.");

        if (list.size() == 1 && list.get(0) == null)
            return null;

        return list.isEmpty()? null:list.get(0);
    }

    public int executeUpdate(Update update) throws SQLException {
        Connection connection = connectionPool.getConnection();

        PreparedStatement preparedStatement = null;
        try {
            String rawSql = update.getRawSql();
            preparedStatement = connection.prepareStatement(rawSql);
            update.prepare(preparedStatement);

            return preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            connectionPool.releaseConnection(connection);
        }
    }

    public void executeBatchUpdate(BatchUpdate update) throws SQLException {
        Connection connection = connectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement statement = connection.prepareStatement(update.getRawSql());

        try {
            int i = 0;
            while ( update.hasNext() ) {
                update.prepare(statement);
                statement.addBatch();

                if ( ((i + 1) % update.executeBatchEvery() == 0) || !update.hasNext() ) {
                    statement.executeBatch();
                }
            }

            statement.executeBatch();
        } finally {
            if (statement != null) {
                statement.close();
            }
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
        }
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static interface SqlOperation
    {
        public String getRawSql();

        public void prepare(PreparedStatement statement) throws SQLException;
    }

    public static interface Update extends SqlOperation
    {}

    public static interface Query<T> extends SqlOperation
    {
        public T fill(ResultSet resultSet) throws SQLException;
    }

    public static interface BatchUpdate extends DatabaseManager.Update {

        boolean hasNext();

        int executeBatchEvery();

    }

}