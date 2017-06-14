package general.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    T apply(ResultSet resultSet) throws SQLException;

}
