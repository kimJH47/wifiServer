package com.core.wifiserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static Connection connection;
    private static final String dbUrl = "jdbc:sqlite:test.db";

    private ConnectionProvider() {
    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection(dbUrl);
            return connection;

        } catch (SQLException e) {
            throw new IllegalStateException("", e);
        }
    }


    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
