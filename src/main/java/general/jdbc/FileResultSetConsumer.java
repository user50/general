package general.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class FileResultSetConsumer implements ResultSetConsumer {

    String fileName;
    Function<ResultSet, String> rawToString;

    public FileResultSetConsumer(String fileName, Function<ResultSet, String> rawToString) {
        this.fileName = fileName;
        this.rawToString = rawToString;
    }

    @Override
    public void accept(ResultSet resultSet) {
        try(PrintWriter output = new PrintWriter(new FileWriter(fileName)))
        {
            while (resultSet.next())
                output.println(rawToString.apply(resultSet));

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
