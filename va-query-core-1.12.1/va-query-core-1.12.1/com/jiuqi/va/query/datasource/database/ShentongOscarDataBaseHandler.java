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

public class ShentongOscarDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public ShentongOscarDataBaseHandler() {
        super("SHENTONG_OSCAR", "Oscar(\u795e\u901a)", "\u795e\u901a\u6570\u636e\u5e93", "com.oscar.Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE GLOBAL TEMPORARY TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  NVARCHAR2(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   NVARCHAR2(200), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   NUMBER(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   NUMBER(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   NUMBER(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n)  \n ON COMMIT PRESERVE ROWS ;  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return "jdbc:oscar://" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getDataBaseName();
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        return sql + " OFFSET " + (pageNumber - 1) * pageSize + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY";
    }
}

