package general.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.function.Function;

public class ResultSetIteratorProvider<T> implements Closeable {

    private Connection connection;
    private String sqlQuery;
    private RowMapper<T> dataMapper;

    public ResultSetIteratorProvider(Connection connection, String sqlQuery, RowMapper<T> dataMapper) {
        this.connection = connection;
        this.sqlQuery = sqlQuery;
        this.dataMapper = dataMapper;
    }

    public Iterator<T> get() throws SQLException {

        PreparedStatement statement = connection.prepareStatement(
                sqlQuery,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

        statement.setFetchSize(Integer.MIN_VALUE);

        return new ResultSetIterator<T>(statement.executeQuery(), dataMapper);
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
