package com.core.wifiserver.dao;

import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import java.time.LocalDateTime;

public class HistoryDao {
    private final JdbcContext jdbcContext;
    private static final String TABLE_NAME = "HISTORY";

    public HistoryDao() {
        this.jdbcContext = JdbcContext.getInstance();
    }

    public int save(double latitude, double longitude) {
        return jdbcContext.insert(QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("latitude").value(String.valueOf(latitude))
                .addColumn("longitude").value(String.valueOf(longitude))
                .addColumn("create_date").value(LocalDateTime.now().toString())
                .build());
    }

    public int delete(int id) {
        return jdbcContext.delete(QueryBuilderFactory.createDeleteQueryBuilder(TABLE_NAME)
                .where(String.format("id = %d", id))
                .build());
    }
}
