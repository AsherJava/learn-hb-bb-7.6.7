/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.AbstractDbSqlHandler;

public class Db2SqlHandler
extends AbstractDbSqlHandler {
    @Override
    public String newUUID() {
        return "UDF_UUID_CREATE()";
    }

    @Override
    public String nullToValue(String field, String defaultValue) {
        return String.format("Coalesce(%1$s,%2$s,%1$s)", field, defaultValue);
    }

    @Override
    public String toDate(String date) {
        return "TO_DATE('" + date + "','YYYY-MM-DD')";
    }

    @Override
    public String addDates(String date, String days) {
        return String.format("(%1$s + %2$s DAYS)", date, days);
    }

    @Override
    public String length(String targetStr) {
        return "SPACE(" + targetStr + ")";
    }

    @Override
    public String IndexStrSql(String str1, String str2, int index) {
        return String.format("LOCATE(%1$s,'%2$s',%3$d)", str1, str2, index);
    }

    @Override
    public String toInt(String field) {
        return "INT(" + field + ")";
    }

    @Override
    public String judgeEmpty(String field, boolean emptyFlag) {
        if (emptyFlag) {
            return String.format("(%1$s is null or %1$s = '')", field);
        }
        return String.format("(%1$s is not null and %1$s <> '')", field);
    }

    @Override
    public String getVirtualTable() {
        return "SYSIBM.DUAL";
    }

    @Override
    public String getPageSql(String sql, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        if (start == 1) {
            return String.format("%1$s FETCh FIRST %2$d ROWS ONLY ", sql, end);
        }
        return String.format("SELECT * FROM (SELECT T.*, ROW_NUMBER() OVER() AS ROWNUM FROM (%1$s \n) T) WHERE ROWNUM BETWEEN %2$d AND %3$d", sql, start, end);
    }

    @Override
    public String getTableColumnSql(String tableName) {
        return null;
    }

    @Override
    public String getTableIndexSql(String tableName) {
        return null;
    }
}

