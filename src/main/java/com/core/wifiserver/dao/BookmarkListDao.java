package com.core.wifiserver.dao;

import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import com.core.wifiserver.dto.BookmarkListDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListDao {
    private final JdbcContext jdbcContext;
    private static final String TABLE_NAME = "BOOKMARK_LIST";

    public BookmarkListDao() {
        this.jdbcContext = JdbcContext.getInstance();
    }

    public int save(String name, String wifiName, int fk) {
        String query = QueryBuilderFactory.createInsertQueryBuilder(TABLE_NAME)
                .addColumn("bookmark_group_name").value(name)
                .addColumn("wifi_name").value(wifiName)
                .addColumn("create_date").value(LocalDateTime.now().toString())
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

    public List<BookmarkListDto> findAll() {
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .build();
        return jdbcContext.select(query, resultSet -> {
            ArrayList<BookmarkListDto> bookmarkListDtos = new ArrayList<>();
            while (resultSet.next()) {
                bookmarkListDtos.add(
                        new BookmarkListDto(resultSet.getInt("id"),
                                resultSet.getString("bookmark_group_name"),
                                resultSet.getString("wifi_name"),
                                resultSet.getString("create_date")
                        )
                );
            }
            return bookmarkListDtos;
        });

    }

}
