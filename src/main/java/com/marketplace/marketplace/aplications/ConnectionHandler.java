package com.marketplace.marketplace.aplications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {

    private static final String url = "jdbc:mysql://localhost:3306/" + "marketplace?&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "root";
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
