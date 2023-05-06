package com.core.wifiserver.dao;

import com.core.wifiserver.exception.DatabaseInoperativeException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionProvider {

    static DataSource dataSource;

    private ConnectionProvider() {
    }

    static {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/SQLiteDB");
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            throw new DatabaseInoperativeException(e);
        }
    }

    public static Connection getConnection() {
        try {

            return dataSource.getConnection();

        } catch (SQLException e) {
            throw new DatabaseInoperativeException(e);
        }
    }


    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseInoperativeException(e);
        }
    }

    public static void startTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            close(connection);
            throw new DatabaseInoperativeException(e);

        }
    }

    public static void endTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DatabaseInoperativeException(e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseInoperativeException(e);
        }
    }
}
