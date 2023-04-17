package com.core.wifiserver.dao.template;

import com.core.wifiserver.dao.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcContext {

    public long insertBulk(List<String> queries) {
        return workWithStatementBatchStrategy(c -> {
            Statement statement = c.createStatement();
            for (String query : queries) {
                statement.addBatch(query);
            }
            return statement;
        });
    }

    private long workWithStatementBatchStrategy(StatementStrategy statementStrategy) {
        Connection c = ConnectionProvider.getConnection();
        ConnectionProvider.startTransaction(c);
        try (Statement stmt = statementStrategy.makeStatement(c)) {
            int insertLow = stmt.executeBatch().length;
            ConnectionProvider.endTransaction(c);
            return insertLow;

        } catch (SQLException e) {
            ConnectionProvider.rollback(c);
            throw new IllegalStateException(e);
        } finally {
            ConnectionProvider.close(c);
        }
    }
}
