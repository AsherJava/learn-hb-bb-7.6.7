/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.AbstractDbSqlHandler;

public class SqlServerSqlHandler
extends AbstractDbSqlHandler {
    @Override
    public String newUUID() {
        return "NEWID()";
    }

    @Override
    public String formatDate(String date, String format) {
        return " format(" + date + ",'" + format + "')";
    }

    @Override
    public String getVirtualTable() {
        return "";
    }

    @Override
    public String concat(String ... args) {
        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append("+").append(arg);
        }
        return result.toString();
    }

    @Override
    public String concatBySeparator(String separatorStr, String ... args) {
        Assert.isNotEmpty(args);
        if (args.length == 1) {
            return args[0];
        }
        StringBuffer result = new StringBuffer();
        for (String argStr : args) {
            if (result.length() > 0) {
                result.append("+'").append(separatorStr).append("'+");
            }
            result.append(argStr);
        }
        return result.toString();
    }

    @Override
    public String SubStr(String filed, String beginIndex, String length) {
        if (!StringUtils.isEmpty(length)) {
            return String.format("SubString(%1$s,%2$s,%3$s)", filed, beginIndex, length);
        }
        return String.format("SubString(%1$s,%2$s,%3$s)", filed, beginIndex, "len(" + filed + ") - (" + beginIndex + ") + 1 ");
    }

    @Override
    public String getPageSql(String sql, int pageNumber, int pageSize) {
        return sql;
    }

    @Override
    public String hex(String target, boolean isConst) {
        if (isConst) {
            return "CONVERT(VARCHAR(32) ,'" + target + "',2)";
        }
        return "CONVERT(VARCHAR(32) ," + target + ",2)";
    }

    @Override
    public String unHex(String target, boolean isConst) {
        if (isConst) {
            return "CONVERT(VARBINARY ,'" + target + "',2)";
        }
        return "CONVERT(VARBINARY ," + target + ",2)";
    }

    @Override
    public String toChar(String field) {
        return "cast(" + field + " as varchar)";
    }

    @Override
    public String toDate(String field) {
        return "cast(" + field + " as datetime)";
    }

    @Override
    public String toDate(String date, String dateFormat) {
        return this.toDate(date);
    }

    @Override
    public String toInt(String field) {
        return "cast(" + field + " as INT)";
    }

    @Override
    public String lpad(String str, int len, String padStr) {
        return "REPLICATE('" + padStr + "'," + len + " - DATALENGTH(" + str + ")) + " + str;
    }

    @Override
    public String year(String date) {
        return "DATEPART(YEAR," + date + ")";
    }

    @Override
    public String month(String date) {
        return "DATEPART(MONTH," + date + ")";
    }

    @Override
    public String day(String date) {
        return "DATEPART(DAY," + date + ")";
    }

    @Override
    public String getTableColumnSql(String tableName) {
        return "select sc.name as columnName from syscolumns sc, sysobjects so, systypes st  where sc.id = so.id and sc.xtype = st.xusertype and so.xtype in ('u','v') and so.name = '" + tableName.toUpperCase() + "'";
    }

    @Override
    public String getTableIndexSql(String tableName) {
        return null;
    }

    @Override
    public String multiRowMerge(String field, String separator, boolean distinct) {
        return String.format("STRING_AGG(%s, '%s') WITHIN GROUP (ORDER BY %s)", field, separator, field);
    }
}

