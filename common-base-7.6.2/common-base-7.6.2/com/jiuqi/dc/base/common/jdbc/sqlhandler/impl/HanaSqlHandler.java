/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.AbstractDbSqlHandler;

public class HanaSqlHandler
extends AbstractDbSqlHandler {
    @Override
    public String newUUID() {
        return "to_char(sysuuid)";
    }

    @Override
    public String toDate(String date) {
        return "'" + date + "'";
    }

    @Override
    public String getPageSql(String sql, int pageNumber, int pageSize) {
        return sql + " limit " + pageSize + " offset " + (pageNumber - 1) * pageSize;
    }

    @Override
    public String indexStrSql(String str1, String str2) {
        return String.format("LOCATE(%1$s, %2$s)", str1, str2);
    }

    @Override
    public String addDates(String date, String days) {
        return String.format("ADD_DAYS(%1$s,%2$s)", date, days);
    }

    @Override
    public String judgeEmpty(String field, boolean emptyFlag) {
        if (emptyFlag) {
            return String.format("(%1$s is null or %1$s = '')", field);
        }
        return String.format("(%1$s is not null and %1$s <> '')", field);
    }

    @Override
    public String toInt(String field) {
        return "to_int(" + field + ")";
    }

    @Override
    public String toDecimal(String field, int precision, int scale) {
        return String.format("TO_DECIMAL(%1$s, %2$d, %3$d)", field, precision, scale);
    }

    @Override
    public String getVirtualTable() {
        return "DUMMY";
    }

    @Override
    public String getTableColumnSql(String tableName) {
        return "SELECT COLUMN_NAME as columnName FROM TABLE_COLUMNS WHERE SCHEMA_NAME = CURRENT_SCHEMA AND TABLE_NAME = '" + tableName.toUpperCase() + "'\nUNION ALL\n SELECT COLUMN_NAME as columnName FROM VIEW_COLUMNS WHERE SCHEMA_NAME = CURRENT_SCHEMA AND VIEW_NAME = '" + tableName.toUpperCase() + "'";
    }

    @Override
    public String getTableIndexSql(String tableName) {
        return " select INDEX_NAME as indexName ,COLUMN_NAME as columnName from INDEX_COLUMNS where SCHEMA_NAME = CURRENT_SCHEMA AND TABLE_NAME = '" + tableName.toUpperCase() + "' ORDER BY index_name, position";
    }

    @Override
    public String getTempTableJudgeSql(String tableName) {
        return String.format("  SELECT COUNT(*) FROM TABLES T WHERE SCHEMA_NAME = CURRENT_SCHEMA AND TABLE_NAME = '%1$s' AND IS_TEMPORARY = TRUE", tableName.toUpperCase());
    }

    @Override
    public String multiRowMerge(String field, String separator, boolean distinct) {
        return String.format("STRING_AGG(%s, '%s')", field, separator);
    }
}

