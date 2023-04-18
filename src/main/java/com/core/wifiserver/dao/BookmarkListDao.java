package com.core.wifiserver.dao;

import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;

public class BookmarkListDao {
    private final JdbcContext jdbcContext;
    private static final String TABLE_NAME = "BOOKMARK_LIST";

    public BookmarkListDao() {
        this.jdbcContext = JdbcContext.getInstance();
    }

    public int save(String name, String wifiName, int fk) {
        String query = QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("name").value(name)
                .addColumn("wifi_name").value(wifiName)
                .addColumn("bookmark_group_id").value(fk)
                .build();
        return jdbcContext.executeSQL(query);
    }

    public int delete(int id) {
        String query = QueryBuilderFactory.createDeleteQueryBuilder(TABLE_NAME)
                .where(String.format("id = %d", id))
                .build();
        return jdbcContext.executeSQL(query);
    }


}
