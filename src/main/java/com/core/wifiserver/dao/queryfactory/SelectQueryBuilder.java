package com.core.wifiserver.dao.queryfactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectQueryBuilder {
    private final String tableName;
    private final List<String> columns;

    private String where;
    private String orderBy;
    private String page;

    public SelectQueryBuilder(String tableName) {
        columns = new ArrayList<>();
        where = "";
        orderBy = "";
        page = "";
        this.tableName = tableName;
    }

    public SelectQueryBuilder columns(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public SelectQueryBuilder orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public SelectQueryBuilder page(int pageSize, int offset) {
        page = String.format("LIMIT %d OFFSET %d", pageSize, offset);
        return this;
    }

    public String build() {
        return "SELECT " + createColumns() + " FROM " + tableName + " "
                + createWhere()
                + createOrderBy()
                + createPage();
    }

    private String createColumns() {
        if (columns.isEmpty()) {
            return "*";
        }
        return String.join(",", columns);
    }

    private String createWhere() {
        if (where.isEmpty()) {
            return "";
        }
        return "WHERE " + where + " ";
    }

    private String createOrderBy() {
        if (orderBy.isEmpty()) {
            return "";
        }
        return "ORDER BY " + orderBy + " ";
    }

    private String createPage() {
        return page;
    }
}
