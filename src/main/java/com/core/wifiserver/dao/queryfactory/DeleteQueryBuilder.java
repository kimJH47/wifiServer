package com.core.wifiserver.dao.queryfactory;

public class DeleteQueryBuilder {

    private String tableName;
    private String where;

    public DeleteQueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public DeleteQueryBuilder where(String where) {
        //condition 분리 x
        this.where = where;
        return this;
    }

    public String build() {
        if (where.isEmpty()) {
            throw new IllegalArgumentException("");
        }
        return "DELETE FROM " + tableName + " where " + where;
    }

}
