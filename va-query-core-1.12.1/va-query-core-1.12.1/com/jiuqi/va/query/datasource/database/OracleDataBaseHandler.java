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

public class OracleDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public OracleDataBaseHandler() {
        super("ORACLE", "Oracle", "oracle", "oracle.jdbc.driver.OracleDriver");
    }

    public String getCreateTempTableSql() {
        return "CREATE GLOBAL TEMPORARY TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  NVARCHAR2(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   NVARCHAR2(200), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   NUMBER(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   NUMBER(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   NUMBER(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n)  \n ON COMMIT PRESERVE ROWS ;  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return "jdbc:oracle:thin:@" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getDataBaseName();
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        int end = pageNumber * pageSize;
        if (start == 0) {
            return String.format(" select tmp.* from ( %1$s ) tmp where rownum <= %2$s", sql, end);
        }
        String pageSql = " select * from (select tmp.*, rownum rn from ( %1$s ) tmp %2$s ) %3$s";
        return String.format(pageSql, sql, " where rownum <= " + end, " where rn > " + start);
    }
}

