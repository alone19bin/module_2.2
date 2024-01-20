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

    public static Connection getConnection(){
        if(connection == null){
            try { connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e){
                e.printStackTrace();
                System.out.println("Error in get connection");
            }
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException{

        return getConnection().prepareStatement(sql);
    }
}
