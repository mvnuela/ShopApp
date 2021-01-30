package edu.pwr.db.model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private java.sql.Connection conn;
    private DataSource dataSource;

    public void connect(String userName, String password) throws SQLException {
        if (conn != null) {
            conn.close();
        }
        dataSource = DataSourceFactory.getMySQLDataSource(userName, password);
        conn = dataSource.getConnection();
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        conn = null;
    }

    public Connection getConn() throws SQLException {
        return conn;
    }

    public SmallItemJdbcTemplate getSmallItemTemplate(String tableName) throws SQLException {
        var r = new SmallItemJdbcTemplate(tableName);
        r.setDataSource(dataSource);
        return r;
    }

    public CoverageLevelJdbcTemplate getCoverageLevelTemplate() throws SQLException {
        var r = new CoverageLevelJdbcTemplate();
        r.setDataSource(dataSource);
        return r;
    }
}

class DataSourceFactory {
    public static DataSource getMySQLDataSource(String userName, String password) {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/shop?sslMode=DISABLED");
        mysqlDS.setUser(userName);
        mysqlDS.setPassword(password);
        mysqlDS.setCharacterEncoding("utf-8");
        return mysqlDS;
    }
}