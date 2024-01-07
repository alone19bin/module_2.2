package org.maxim.crud.Utilc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    private static JDBCUtils jdbcUtils;
    private static Connection connection;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost/database1";
    static final String USER = "root";
    static final String PASSWORD = "root";

    private JDBCUtils() {

}

    public static JDBCUtils getInstance(){
        if(jdbcUtils == null){
            jdbcUtils = new JDBCUtils();
        }
        return jdbcUtils;
    }
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
        try {
            return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
