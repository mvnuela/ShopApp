package edu.pwr.db.model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DBConnection {
    private java.sql.Connection conn;

    public void connect(String userName, String password) {
        try {
            if (conn != null) {
                conn.close();
            }
            conn = DataSourceFactory.getMySQLDataSource(userName, password).getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        conn = null;
    }
}

class DataSourceFactory {
    public static DataSource getMySQLDataSource(String userName, String password) {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("mysql://localhost:3306/Shop");
        mysqlDS.setUser(userName);
        mysqlDS.setPassword(password);
        return mysqlDS;
    }
}