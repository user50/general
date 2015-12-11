package general.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class FileResultSetConsumer implements ResultSetConsumer {

    String fileName;
    Function<ResultSet, String> rowToString;

    public FileResultSetConsumer(String fileName, Function<ResultSet, String> rowToString) {
        this.fileName = fileName;
        this.rowToString = rowToString;
    }

    @Override
    public void accept(ResultSet resultSet) {
        try(PrintWriter output = new PrintWriter(new FileWriter(fileName)))
        {
            while (resultSet.next())
                output.println(rowToString.apply(resultSet));

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
