package general.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * All objects that implements this interface, is able to extract and interpret data of a ResultSet
 *
 */
public interface RowMapper<T> {
    public void read(ResultSet resultSet) throws SQLException;

    T getResult();

}
