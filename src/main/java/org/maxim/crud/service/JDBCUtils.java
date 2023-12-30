package org.maxim.crud.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    static final String DATABASE_URL_KEY = "com.mysql.cj.jdbc.Driver";
    static final String USER_KEY = "root";
    static final String PASSWORD_KEY = "root";


    public static Connection getConnectJDBC() {
        System.out.println("Registering JDBC driver");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;


        try {
            connection = DriverManager.getConnection(DATABASE_URL_KEY, USER_KEY, PASSWORD_KEY);
            connection.setAutoCommit(false);
            System.out.println("Creating database connection");
        } catch (SQLException e) {
            System.out.println("Error in connection: " + e.getMessage());
        }

        return connection;
    }
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
