/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.database.AbstractQueryDataBaseHandler
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 */
package com.jiuqi.va.query.datasource.database;

import com.jiuqi.va.query.datasource.database.AbstractQueryDataBaseHandler;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;

public class SqlServerDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public SqlServerDataBaseHandler() {
        super("SQL_SERVER", "SqlServer", "microsoft sql server", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    public String getCreateTempTableSql() {
        return "CREATE TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  VARCHAR(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   VARCHAR(120), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   DECIMAL(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   DATETIME2  \n);  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return "jdbc:sqlserver://" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + ";DatabaseName=" + dataSourceInfo.getDataBaseName();
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        if (!this.containsIgnoreCase(sql, "order by ")) {
            return sql + " order by current_timestamp offset " + start + "  rows fetch next " + pageSize + " rows only ";
        }
        return sql + " offset " + start + "  rows fetch next " + pageSize + " rows only ";
    }

    public boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int length = searchStr.length();
        if (length == 0) {
            return true;
        }
        for (int i = str.length() - length; i >= 0; --i) {
            if (!str.regionMatches(true, i, searchStr, 0, length)) continue;
            return true;
        }
        return false;
    }
}

