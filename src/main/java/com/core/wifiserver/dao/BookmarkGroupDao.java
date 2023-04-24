package com.core.wifiserver.dao;

import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import com.core.wifiserver.dto.BookmarkGroupDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDao {

    private static final String TABLE_NAME = "BOOKMARK_GROUP";
    private final JdbcContext jdbcContext;

    public BookmarkGroupDao() {
        this.jdbcContext = JdbcContext.getInstance();
    }

    public int save(String name, int order) {
        if (isSameOrder(order)) {
            updateAllOrder(order);
        }
        String query = QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("name").value(name)
                .addColumn("orders").value(order)
                .addColumn("create_date").value(LocalDateTime.now().toString())
                .addColumn("modified_date").value("")
                .build();
        return jdbcContext.executeSQL(query);
    }

    public int update(int id, int order, String name) {
        if (isSameOrder(order)) {
            updateAllOrder(order);
        }
        String query = QueryBuilderFactory.createUpdateQueryBuilder(TABLE_NAME)
                .addColumn("name").value(name)
                .addColumn("orders").value(order)
                .addColumn("modified_date").value(LocalDateTime.now().toString())
                .where(String.format("id = %d", id))
                .build();
        return jdbcContext.executeSQL(query);
    }

    private boolean isSameOrder(int order) {
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .columns("COUNT(orders)")
                .where(String.format("orders = %d", order))
                .build();

        return jdbcContext.select(query, resultSet -> resultSet.getInt(1) >= 1);
    }

    private void updateAllOrder(int order) {
        String query = "update BOOKMARK_GROUP"
                + " set ORDERS = ORDERS + 1"
                + ",modified_date = " + String.format("\"%s\"",LocalDateTime.now())
                + " where ORDERS >= " + order;
        jdbcContext.executeSQL(query);
    }

    public int delete(int id) {
        String query = QueryBuilderFactory.createDeleteQueryBuilder(TABLE_NAME)
                .where(String.format("id = %d", id))
                .build();
        return jdbcContext.executeSQL(query);
    }

    public List<BookmarkGroupDto> findAll() {
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .build();
        return jdbcContext.select(query, resultSet -> {
            ArrayList<BookmarkGroupDto> bookmarkGroupDtos = new ArrayList<>();
            while (resultSet.next()) {
                bookmarkGroupDtos.add(new BookmarkGroupDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("orders"),
                        resultSet.getString("create_date"),
                        resultSet.getString("modified_date")
                ));
            }
            return bookmarkGroupDtos;
        });
    }
}
