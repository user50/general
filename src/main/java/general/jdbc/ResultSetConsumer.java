package general.jdbc;

import java.sql.ResultSet;

public interface ResultSetConsumer  {

    void accept(ResultSet resultSet);

}
