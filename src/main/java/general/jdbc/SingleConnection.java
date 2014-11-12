package general.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by user50 on 12.11.2014.
 */
public class SingleConnection implements ConnectionPool {

    private String url;
    private String user;
    private String password;

    Connection connection;

    public SingleConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public SingleConnection(String host, String user, String psw, String database) {
        this.url = "jdbc:mysql://"+host+":3306/"+database;
        this.user = user;
        this.password = psw;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(url, user, password);


        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) throws SQLException {
//        connection.close();
    }
}
