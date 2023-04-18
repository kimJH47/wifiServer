package com.core.wifiserver.dao.template;

import com.core.wifiserver.dao.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcContext {
    static JdbcContext jdbcContext;

    private JdbcContext() {

    }

    public static JdbcContext getInstance() {
        if (jdbcContext != null) {
            return jdbcContext;
        }
        jdbcContext = new JdbcContext();
        return jdbcContext;
    }

    public long insertBulk(List<String> queries) {
        return workWithStatementBatchStrategy(c -> {
            Statement statement = c.createStatement();
            for (String query : queries) {
                statement.addBatch(query);
            }
            return statement;
        });
    }

    public int insert(String query) {
        return workWithStatementStrategy(query, Connection::createStatement);
    }

    public int delete(String query) {
        return workWithStatementStrategy(query, Connection::createStatement);
    }

    public int update(String query) {
        return workWithStatementStrategy(query, Connection::createStatement);
    }

    private int workWithStatementStrategy(String query, StatementStrategy statementStrategy) {
        Connection connection = ConnectionProvider.getConnection();
        try (Statement stmt = statementStrategy.makeStatement(connection)) {
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionProvider.close(connection);
        }
    }

    private long workWithStatementBatchStrategy(StatementStrategy statementStrategy) {
        Connection connection = ConnectionProvider.getConnection();
        ConnectionProvider.startTransaction(connection);
        try (Statement stmt = statementStrategy.makeStatement(connection)) {
            int insertLow = stmt.executeBatch().length;
            ConnectionProvider.endTransaction(connection);
            return insertLow;

        } catch (SQLException e) {
            ConnectionProvider.rollback(connection);
            throw new IllegalStateException(e);
        } finally {
            ConnectionProvider.close(connection);
        }
    }

    public <T> T select(String query, LowMapper<T> lowMapper) {
        return workWithStatementStrategy(Connection::createStatement, query, lowMapper);
    }

    private <T> T workWithStatementStrategy(StatementStrategy statementStrategy, String query, LowMapper<T> lowMapper) {
        Connection connection = ConnectionProvider.getConnection();
        try (Statement statement = statementStrategy.makeStatement(connection)) {
            return lowMapper.mapRow(statement.executeQuery(query));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionProvider.close(connection);
        }
    }
}
