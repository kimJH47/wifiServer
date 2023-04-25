package com.core.wifiserver.dao.queryfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

public class InsertQueryBuilder {
    private final String query;
    private final List<String> columns;
    private final List<Object> values;

    public InsertQueryBuilder(String query) {
        this.query = query;
        this.columns = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public ValueBuilder addColumn(String column) {
        columns.add(column);
        return new ValueBuilder(this);
    }

    private InsertQueryBuilder addValue(Object condition) {
        values.add(condition);
        return this;
    }

    public String build() {
        if (values.size() != columns.size()) {
            throw new IllegalStateException("column 과 values 가 일치 하지 않음");
        }
        StringBuilder queryBuilder = new StringBuilder(this.query);
        String columnsQuery = columns.stream()
                .collect(Collectors.joining(",", "(", ")"));
        queryBuilder.append(columnsQuery)
                .append(" values(");

        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            queryBuilder.append(create(value));
            if (i < values.size() - 1) {
                queryBuilder.append(",");
            }
        }
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private String create(Object value) {
        if (value instanceof Integer) {
            return value.toString();
        }
        return String.format("\"%s\"", value);
    }

    @RequiredArgsConstructor
    public static class ValueBuilder {
        private final InsertQueryBuilder insertQueryBuilder;

        public InsertQueryBuilder value(Object condition) {
            return insertQueryBuilder.addValue(condition);
        }
    }
}
