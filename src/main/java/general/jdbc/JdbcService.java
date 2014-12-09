package general.jdbc;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {

    private DataSource dataSource;

    public JdbcService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeQuery(SqlOperation query, RowMapper<?> rowMapper) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.getRawSql())) {
            query.prepare(preparedStatement);

            try(ResultSet resultSet = preparedStatement.executeQuery())
            {
                rowMapper.read(resultSet);
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

                if ( ((i + 1) % update.executeBatchEvery() == 0) ) {
                    statement.executeBatch();
                }
            }

            statement.executeBatch();
            connection.setAutoCommit(true);
        }
    }
}
