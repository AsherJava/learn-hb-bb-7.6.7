/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler;

public interface IDbSqlHandler {
    public String newUUID();

    public String toDate(String var1);

    public String toDate(String var1, String var2);

    public String formatDate(String var1, String var2);

    public String serialDateFmt();

    public String hyphenDateFmt();

    public String hyphenTimeStampFmt();

    public String year(String var1);

    public String month(String var1);

    public String day(String var1);

    public String trim(String var1);

    public String length(String var1);

    public String concat(String ... var1);

    public String concatBySeparator(String var1, String ... var2);

    public String getPageSql(String var1, int var2, int var3);

    public String indexStrSql(String var1, String var2);

    public String addDates(String var1, String var2);

    public String judgeEmpty(String var1, boolean var2);

    public String toChar(String var1);

    public String toClob(String var1);

    public String nullToValue(String var1, String var2);

    public String toInt(String var1);

    public String lpad(String var1, String var2);

    public String lpad(String var1, int var2, String var3);

    public String toDecimal(String var1, int var2, int var3);

    public String getVirtualTable();

    public String SubStr(String var1, String var2);

    public String SubStr(String var1, String var2, String var3);

    public String IndexStrSql(String var1, String var2, int var3);

    public String hex(String var1, boolean var2);

    public String unHex(String var1, boolean var2);

    public String getTableColumnSql(String var1);

    public String getTableIndexSql(String var1);

    public String power(String var1, String var2);

    public String getTempTableJudgeSql(String var1);

    public String multiRowMerge(String var1, String var2, boolean var3);
}

