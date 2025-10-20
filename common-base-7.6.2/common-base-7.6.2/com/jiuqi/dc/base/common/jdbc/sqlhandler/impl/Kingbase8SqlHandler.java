/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler;

public class Kingbase8SqlHandler
extends PostgreSQLSqlHandler {
    @Override
    public String newUUID() {
        return "SYS_GUID()";
    }

    @Override
    public String IndexStrSql(String str1, String str2, int index) {
        if (index >= 0) {
            return String.format("InStr(%1$s,'%2$s',%3$d)", str1, str2, index);
        }
        return String.format("InStr(REVERSE(%1$s),'%2$s',abs(%3$d))", str1, str2, index);
    }

    @Override
    public String judgeEmpty(String field, boolean emptyFlag) {
        if (emptyFlag) {
            return String.format("(%1$s is null)", field);
        }
        return String.format("(%1$s is not null)", field);
    }

    @Override
    public String getTableIndexSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.relname AS indexName, a.attname AS columnName  \n");
        sql.append("  FROM pg_index i  \n");
        sql.append("  JOIN pg_class c ON c.oid = i.indexrelid  \n");
        sql.append("  JOIN pg_class t ON t.oid = i.indrelid  \n");
        sql.append("  JOIN pg_am am ON c.relam = am.oid  \n");
        sql.append("  CROSS JOIN LATERAL unnest(i.indkey) WITH ORDINALITY pos(attnum, ordinality)  \n");
        sql.append("  JOIN pg_attribute a ON a.attnum = pos.attnum AND a.attrelid = t.oid  \n");
        sql.append(" WHERE t.relname = '").append(tableName.toUpperCase()).append("' \n");
        sql.append(" ORDER BY c.relname, pos.ordinality");
        return sql.toString();
    }
}

