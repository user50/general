package general.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * The interface describes objects that define a sql query and parameters of the query
 */
public interface SqlOperation {
    public String getRawSql();

    public void prepare(PreparedStatement statement) throws SQLException;
}
