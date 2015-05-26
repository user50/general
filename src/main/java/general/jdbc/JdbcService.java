package general.jdbc;


import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcService {

    private DataSource dataSource;

    @Inject
    public JdbcService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T executeQuery(SqlOperation query, ResultSetExtractor<T> resultSetExtractor) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.getRawSql())) {
            query.prepare(preparedStatement);

            try(ResultSet resultSet = preparedStatement.executeQuery())
            {
                return resultSetExtractor.read(resultSet);
            }
        }
    }

    public <T> T executeQueryByCursor(SqlOperation query, ResultSetExtractor<T> resultSetExtractor) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    query.getRawSql(),
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY
            );
            statement.setFetchSize(Integer.MIN_VALUE);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSetExtractor.read(resultSet);
            }
        }
    }

    public int executeUpdate(SqlOperation update) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update.getRawSql())) {

            update.prepare(preparedStatement);

            return preparedStatement.executeUpdate();
        }
    }

    public void executeBatchUpdate(BatchUpdate update) throws SQLException {

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(update.getRawSql())) {
            connection.setAutoCommit(false);

            int i = 0;
            while ( update.hasNext() ) {
                update.prepare(statement);
                statement.addBatch();

                if ( ((i++ + 1) % update.executeBatchEvery() == 0) ) {
                    statement.executeBatch();
                }
            }

            statement.executeBatch();
            connection.setAutoCommit(true);
        }
    }
}
