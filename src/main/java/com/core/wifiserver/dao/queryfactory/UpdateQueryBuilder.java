package com.core.wifiserver.dao.queryfactory;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

public class UpdateQueryBuilder {
    private String tableName;
    private final List<String> columns;
    private final List<Object> values;

    private String where;

    public UpdateQueryBuilder(String tableName) {
        columns = new ArrayList<>();
        values = new ArrayList<>();
        this.tableName = tableName;
        this.where = "";

    }

    public ValueBuilder addColumn(String column) {
        columns.add(column);
        return new ValueBuilder(this);
    }

    private UpdateQueryBuilder value(Object value) {
        values.add(value);
        return this;
    }

    public UpdateQueryBuilder where(String where) {
        this.where = where;
        return this;
    }

    public String build() {
        StringBuilder setBuilder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (value instanceof Integer) {
                setBuilder.append(columns.get(i)).append("=").append(value);
                continue;
            }
            setBuilder.append(columns.get(i))
                    .append("=")
                    .append("\"")
                    .append(value)
                    .append("\"");

            if (i < values.size() - 1) {
                setBuilder.append(",");
            }

        }
        return "UPDATE " + tableName + " SET " + setBuilder + " " + where;
    }

    @RequiredArgsConstructor
    public static class ValueBuilder {
        private final UpdateQueryBuilder updateQueryBuilder;

        public UpdateQueryBuilder value(Object value) {
            return updateQueryBuilder.value(value);
        }
    }
}
