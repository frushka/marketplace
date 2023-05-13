package com.marketplace.marketplace.aplications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {


    static String url = "jdbc:mysql://localhost:3306/" + "marketplace?&serverTimezone=UTC";
    static String user = "root";
    static String password = "root";
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
