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
public class DremioDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public static final String DREMIO = "Dremio";

    public DremioDataBaseHandler() {
        super(DREMIO, DREMIO, DREMIO, "com.dremio.jdbc.Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  VARCHAR(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   VARCHAR(120), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   DECIMAL(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n);  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return String.format("jdbc:dremio:direct=%1$s:%2$s", dataSourceInfo.getIp(), dataSourceInfo.getPort());
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        String template = "SELECT * FROM ( %1$s ) AS inner1_ LIMIT %2$s OFFSET %3$s";
        return String.format(template, sql, pageSize, (pageNumber - 1) * pageSize);
    }
}

