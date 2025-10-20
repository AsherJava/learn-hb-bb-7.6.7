/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.AbstractDbSqlHandler;

public class OracleSqlHandler
extends AbstractDbSqlHandler {
    @Override
    public String newUUID() {
        return "RAWTOHEX(SYS_GUID())";
    }

    @Override
    public String day(String date) {
        return " to_char(" + date + ",'dd')";
    }

    @Override
    public String getPageSql(String sql, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        int end = pageNumber * pageSize;
        if (start == 0) {
            return String.format(" select tmp.* from ( %1$s ) tmp where rownum <= " + end, sql);
        }
        String pageSql = " select * from (select tmp.*, rownum rn from ( %1$s ) tmp %2$s ) %3$s";
        return String.format(pageSql, sql, " where rownum <= " + end, " where rn > " + start);
    }

    @Override
    public String nullToValue(String field, String defaultValue) {
        return "Nvl(" + field + "," + defaultValue + ")";
    }

    @Override
    public String getTableColumnSql(String tableName) {
        return " select column_name as columnName from user_tab_columns where upper(table_name) = '" + tableName.toUpperCase() + "'";
    }

    @Override
    public String getTableIndexSql(String tableName) {
        return " select index_name as indexName ,column_name as columnName from user_ind_columns where upper(table_name) = '" + tableName.toUpperCase() + "' order by index_name, column_position";
    }
}

