package general.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SimpleSqlOperation implements SqlOperation {

    String sqlQuery;

    public SimpleSqlOperation(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public String getRawSql() {
        return null;
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        //no query parameters
    }
}
