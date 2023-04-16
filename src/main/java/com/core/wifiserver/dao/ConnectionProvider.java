package com.core.wifiserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final String dbUrl = "jdbc:sqlite:test.db";

    private ConnectionProvider() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl);

        } catch (SQLException e) {
            throw new IllegalStateException("", e);
        }
    }


    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void startTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            close(connection);
            throw new IllegalStateException(e);
        }
    }

    public static void endTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new IllegalStateException(e);
        }
    }

    public static void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
