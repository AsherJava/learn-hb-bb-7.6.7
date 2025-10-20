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
import org.springframework.stereotype.Component;

@Component
public class UxDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public UxDataBaseHandler() {
        super("uxdb", "\u4f18\u70ab\u6570\u636e\u5e93", "uxdb", "com.uxsino.uxdb.Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE TABLE DC_QUERY_TEMPTABLE (\nQUERYKEY VARCHAR(120), \n" + ParamTypeEnum.STRING.getTempTableField() + " VARCHAR(120), \n" + ParamTypeEnum.INTEGER.getTempTableField() + " INTEGER, \n" + ParamTypeEnum.NUMBER.getTempTableField() + " NUMERIC(23), \n" + ParamTypeEnum.BOOL.getTempTableField() + " BOOLEAN, \n" + ParamTypeEnum.DATE.getTempTableField() + " DATE, \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + " TIMESTAMP\n)";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return String.format("jdbc:uxdb://%1$s:%2$s/%3$s", dataSourceInfo.getIp(), dataSourceInfo.getPort(), dataSourceInfo.getDataBaseName());
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        String template = "SELECT * FROM ( %1$s ) AS inner1_ LIMIT %2$s OFFSET %3$s";
        return String.format(template, sql, pageSize, (pageNumber - 1) * pageSize);
    }
}

