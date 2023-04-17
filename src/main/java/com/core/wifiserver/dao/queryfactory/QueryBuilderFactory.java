package com.core.wifiserver.dao.queryfactory;

public class QueryBuilderFactory {

    private QueryBuilderFactory() {
    }

    public static InsertQueryBuilder createInsertQueryBuilder(String tableName) {
        String insertBase = String.format("insert into %s", tableName);
        return new InsertQueryBuilder(insertBase);
    }
}
