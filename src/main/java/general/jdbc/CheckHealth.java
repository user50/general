package general.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckHealth implements SqlOperation{
    @Override
    public String getRawSql() {
        return "SELECT 1";
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {

    }
}
