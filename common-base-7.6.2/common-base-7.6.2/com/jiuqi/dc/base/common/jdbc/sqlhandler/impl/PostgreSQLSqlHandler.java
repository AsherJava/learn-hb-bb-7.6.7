/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.AbstractDbSqlHandler;

public class PostgreSQLSqlHandler
extends AbstractDbSqlHandler {
    @Override
    public String newUUID() {
        return "uuid_generate_v4()";
    }

    @Override
    public String length(String targetStr) {
        return "LENGTH(" + targetStr + ")";
    }

    @Override
    public String concat(String ... args) {
        Assert.isNotEmpty(args);
        if (args.length == 1) {
            return args[0];
        }
        StringBuffer result = new StringBuffer();
        result.append("concat(");
        for (String str : args) {
            result.append(str).append(",");
        }
        result.deleteCharAt(result.lastIndexOf(","));
        result.append(")");
        return result.toString();
    }

    @Override
    public String concatBySeparator(String separatorStr, String ... args) {
        Assert.isNotEmpty(args);
        if (args.length == 1) {
            return args[0];
        }
        StringBuffer result = new StringBuffer();
        result.append("concat_ws('").append(separatorStr).append("'");
        for (String str : args) {
            result.append(",").append(str);
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public String getPageSql(String sql, int pageNumber, int pageSize) {
        return sql + " limit " + pageSize + " offset " + (pageNumber - 1) * pageSize;
    }

    @Override
    public String indexStrSql(String str1, String str2) {
        return String.format("strpos(%1$s, %2$s)", str1, str2);
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
        return "cast(" + field + " as int)";
    }

    @Override
    public String toDecimal(String field, int precision, int scale) {
        return "cast(" + field + " as numeric)";
    }

    @Override
    public String SubStr(String filed, String beginIndex, String length) {
        if (!StringUtils.isEmpty(length)) {
            return String.format("SubString(%1$s,%2$s,%3$s)", filed, String.format("(%1$s) + 1 ", beginIndex), length);
        }
        return String.format("SubString(%1$s,%2$s)", filed, beginIndex);
    }

    @Override
    public String toChar(String field) {
        return "cast(" + field + " as varchar)";
    }

    @Override
    public String getTableColumnSql(String tableName) {
        return "select column_name as columnName from information_schema.columns where table_schema = current_schema and table_name = '" + tableName.toLowerCase() + "'";
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
        sql.append(" WHERE t.relname = '").append(tableName.toLowerCase()).append("' \n");
        sql.append(" ORDER BY c.relname, pos.ordinality");
        return sql.toString();
    }

    @Override
    public String multiRowMerge(String field, String separator, boolean distinct) {
        return String.format("STRING_AGG(%s%s::text, '%s')", distinct ? "DISTINCT " : "", field, separator);
    }
}

