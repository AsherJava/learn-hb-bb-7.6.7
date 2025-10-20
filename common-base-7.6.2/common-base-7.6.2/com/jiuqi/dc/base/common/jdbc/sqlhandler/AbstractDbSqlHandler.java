/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;

public abstract class AbstractDbSqlHandler
implements IDbSqlHandler {
    @Override
    public String toDate(String date) {
        return "TO_DATE('" + date + "','yyyy-MM-dd')";
    }

    @Override
    public String toDate(String date, String dateFormat) {
        return String.format("TO_DATE(%1$s, %2$s)", date, dateFormat);
    }

    @Override
    public String formatDate(String date, String format) {
        return " to_char(" + date + ",'" + format + "')";
    }

    @Override
    public String serialDateFmt() {
        return "yyyyMMdd";
    }

    @Override
    public String hyphenDateFmt() {
        return "yyyy-MM-dd";
    }

    @Override
    public String hyphenTimeStampFmt() {
        return "yyyy-MM-dd HH24:MI:ss";
    }

    @Override
    public String year(String date) {
        return " to_char(" + date + ",'yyyy')";
    }

    @Override
    public String month(String date) {
        return " to_char(" + date + ",'mm')";
    }

    @Override
    public String day(String date) {
        return " to_char(" + date + ",'dd')";
    }

    @Override
    public String trim(String targetStr) {
        return "trim(" + targetStr + ")";
    }

    @Override
    public String length(String targetStr) {
        return "length(" + targetStr + ")";
    }

    @Override
    public String concat(String ... args) {
        Assert.isNotEmpty(args);
        if (args.length == 1) {
            return args[0];
        }
        StringBuffer result = new StringBuffer();
        for (String argStr : args) {
            if (result.length() > 0) {
                result.append("||");
            }
            result.append(argStr);
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
                result.append("||'").append(separatorStr).append("'||");
            }
            result.append(argStr);
        }
        return result.toString();
    }

    @Override
    public String indexStrSql(String str1, String str2) {
        return String.format("InStr(%1$s, %2$s)", str1, str2);
    }

    @Override
    public String addDates(String date, String days) {
        return String.format("(%1$s + %2$s)", date, days);
    }

    @Override
    public String judgeEmpty(String field, boolean emptyFlag) {
        return field + (emptyFlag ? " is null" : " is not null");
    }

    @Override
    public String toChar(String field) {
        return "to_char(" + field + ")";
    }

    @Override
    public String toClob(String field) {
        return "to_clob(" + field + ")";
    }

    @Override
    public String nullToValue(String field, String defaultValue) {
        return "Case When " + field + " is null Then " + defaultValue + " Else " + field + " End";
    }

    @Override
    public String toInt(String field) {
        return "to_number(" + field + ")";
    }

    @Override
    public String lpad(String str, String formatExp) {
        return "to_char(" + str + ",'" + formatExp + "')";
    }

    @Override
    public String lpad(String str, int len, String padstr) {
        return "lpad(" + str + "," + len + ",'" + padstr + "')";
    }

    @Override
    public String toDecimal(String field, int precision, int scale) {
        return field;
    }

    @Override
    public String getVirtualTable() {
        return "DUAL";
    }

    @Override
    public String SubStr(String filed, String length) {
        return this.SubStr(filed, "1", length);
    }

    @Override
    public String SubStr(String filed, String beginIndex, String length) {
        if (!StringUtils.isEmpty(length)) {
            return "SubStr(" + filed + "," + beginIndex + "," + length + ")";
        }
        return "SubStr(" + filed + "," + beginIndex + ")";
    }

    @Override
    public String IndexStrSql(String str1, String str2, int index) {
        return String.format("InStr(%1$s,'%2$s',%3$d)", str1, str2, index);
    }

    @Override
    public String hex(String target, boolean isConst) {
        if (isConst) {
            return String.format("rawtohex('%1$s')", target);
        }
        return String.format("rawtohex(%1$s)", target);
    }

    @Override
    public String unHex(String target, boolean isConst) {
        if (isConst) {
            return String.format("hextoraw('%1$s')", target);
        }
        return String.format("hextoraw(%1$s)", target);
    }

    @Override
    public String power(String baseNumber, String numberString) {
        return "POWER(" + baseNumber + ", " + numberString + ")";
    }

    @Override
    public String getTempTableJudgeSql(String tableName) {
        return String.format(" SELECT COUNT(*) FROM USER_TABLES WHERE UPPER(TABLE_NAME) = '%1$S' AND TEMPORARY = 'Y'", tableName.toUpperCase());
    }

    @Override
    public String multiRowMerge(String field, String separator, boolean distinct) {
        return String.format("LISTAGG(%s%s, '%s') WITHIN GROUP (ORDER BY %s)", distinct ? "DISTINCT " : "", field, separator, field);
    }
}

