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

public class DamengDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public DamengDataBaseHandler() {
        super("DAMENG", "DM(\u8fbe\u68a6)", "dm dbms", "dm.jdbc.driver.DmDriver");
    }

    public String getCreateTempTableSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE GLOBAL TEMPORARY TABLE ").append("DC_QUERY_TEMPTABLE").append("\n");
        sql.append("(  \n");
        sql.append("QUERYKEY  VARCHAR(120), \n");
        sql.append(ParamTypeEnum.STRING.getTempTableField()).append("   VARCHAR(120), \n");
        sql.append(ParamTypeEnum.INTEGER.getTempTableField()).append("   NUMBER(23),  \n");
        sql.append(ParamTypeEnum.NUMBER.getTempTableField()).append("   NUMBER(23),  \n");
        sql.append(ParamTypeEnum.BOOL.getTempTableField()).append("   NUMBER(1),  \n");
        sql.append(ParamTypeEnum.DATE.getTempTableField()).append("   DATE,  \n");
        sql.append(ParamTypeEnum.DATE_TIME.getTempTableField()).append("   TIMESTAMP  \n");
        sql.append(")  \n");
        sql.append(" ON COMMIT PRESERVE ROWS ;  \n");
        return sql.toString();
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return "jdbc:dm://" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getDataBaseName();
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        return sql + " limit " + (pageNumber - 1) * pageSize + "," + pageSize;
    }
}

