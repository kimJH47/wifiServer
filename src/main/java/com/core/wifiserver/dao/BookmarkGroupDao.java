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
        String query = QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("name").value(name)
                .addColumn("order").value(order)
                .addColumn("create_date").value(LocalDateTime.now().toString())
                .addColumn("modified_date").value("")
                .build();
        return jdbcContext.insert(query);
    }

    public int delete(int id) {
        String query = QueryBuilderFactory.createDeleteQueryBuilder(TABLE_NAME)
                .where(String.format("id = %d", id))
                .build();
        return jdbcContext.insert(query);
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
                        resultSet.getInt("order"),
                        resultSet.getString("create_date"),
                        resultSet.getString("modified_date")
                ));
            }
            return bookmarkGroupDtos;
        });
    }

    public int update(int id, int order, String name) {
        String query = QueryBuilderFactory.createUpdateQueryBuilder(TABLE_NAME)
                .addColumn("order").value(order)
                .addColumn("name").value(name)
                .addColumn("modified_date").value(LocalDateTime.now().toString())
                .where(String.format("id = %d", id))
                .build();
        return jdbcContext.update(query);
    }

}
