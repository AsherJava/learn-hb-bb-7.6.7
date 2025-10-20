/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.AbstractDbSqlHandler;

public class MySQLSqlHandler
extends AbstractDbSqlHandler {
    @Override
    public String newUUID() {
        return "uuid()";
    }

    @Override
    public String toDate(String date) {
        return "'" + date + "'";
    }

    @Override
    public String toDate(String date, String dateFormat) {
        return String.format("str_to_date(%1$s, %2$s)", date, dateFormat);
    }

    @Override
    public String formatDate(String date, String format) {
        return " date_format(" + date + ",'" + format + "')";
    }

    @Override
    public String serialDateFmt() {
        return "%Y%m%d";
    }

    @Override
    public String hyphenDateFmt() {
        return "%Y-%m-%d";
    }

    @Override
    public String hyphenTimeStampFmt() {
        return "%Y-%m-%d %H:%i:%s";
    }

    @Override
    public String day(String date) {
        return "Day(" + date + ")";
    }

    @Override
    public String length(String targetStr) {
        return "CHAR_LENGTH(" + targetStr + ")";
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
        return sql + " limit " + (pageNumber - 1) * pageSize + "," + pageSize;
    }

    @Override
    public String indexStrSql(String str1, String str2) {
        return String.format("locate(%1$s, %2$s)", str2, str1);
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
        return "cast(" + field + " as SIGNED)";
    }

    @Override
    public String SubStr(String filed, String beginIndex, String length) {
        if (!StringUtils.isEmpty(length)) {
            return String.format("SubString(%1$s,%2$s,%3$s)", filed, beginIndex, length);
        }
        return String.format("SubString(%1$s,%2$s)", filed, beginIndex);
    }

    @Override
    public String IndexStrSql(String str1, String str2, int index) {
        if (index < 0) {
            return String.format("char_length(substr(%1$s,locate('%2$s',reverse(%1$s),%3$s)))", str1, str2, -index);
        }
        return String.format("locate('%1$s','%2$s',%3$s)", str2, str1, index);
    }

    @Override
    public String toChar(String field) {
        return "CONVERT(" + field + ",CHAR)";
    }

    @Override
    public String toClob(String field) {
        return "CONVERT(" + field + ",CHAR)";
    }

    @Override
    public String hex(String target, boolean isConst) {
        if (isConst) {
            return String.format("hex('%1$s')", target);
        }
        return String.format("hex(%1$s)", target);
    }

    @Override
    public String unHex(String target, boolean isConst) {
        if (isConst) {
            return String.format("unhex('%1$s')", target);
        }
        return String.format("unhex(%1$s)", target);
    }

    @Override
    public String getTableColumnSql(String tableName) {
        return "select COLUMN_NAME as columnName from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME ='" + tableName.toUpperCase() + "'";
    }

    @Override
    public String getTableIndexSql(String tableName) {
        return "select INDEX_NAME as indexName ,COLUMN_NAME as columnName from information_schema.STATISTICS where TABLE_SCHEMA = (select database()) and TABLE_NAME ='" + tableName.toUpperCase() + "' order by index_name, seq_in_index";
    }

    @Override
    public String multiRowMerge(String field, String separator, boolean distinct) {
        return String.format("GROUP_CONCAT(%s%s ORDER BY %s SEPARATOR '%s')", distinct ? "DISTINCT " : "", field, field, separator);
    }
}

