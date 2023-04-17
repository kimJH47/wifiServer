package com.core.wifiserver.dao;

import com.core.wifiserver.dao.queryfactory.QueryBuilderFactory;
import com.core.wifiserver.dao.template.JdbcContext;
import com.core.wifiserver.dto.HistoryDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<HistoryDto> findAll() {
        String query = QueryBuilderFactory.createSelectQueryBuilder(TABLE_NAME)
                .build();
        return jdbcContext.select(query, resultSet -> {
            ArrayList<HistoryDto> historyDtos = new ArrayList<>();
            while (resultSet.next()) {
                historyDtos.add(new HistoryDto(
                        resultSet.getInt("id"),
                        Double.parseDouble(resultSet.getString("LATITUDE")),
                        Double.parseDouble(resultSet.getString("LONGITUDE")),
                        resultSet.getString("CREATE_DATE")
                ));
            }
            return historyDtos;
        });
    }
}
