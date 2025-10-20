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
import org.springframework.util.StringUtils;

public class MysqlDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public MysqlDataBaseHandler() {
        super("MYSQL", "MySQL", "mysql", "com.mysql.jdbc.Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  VARCHAR(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   VARCHAR(120), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   DECIMAL(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n);  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        String dataBaseParam = "";
        if (StringUtils.hasText(dataSourceInfo.getDataBaseParam()) && !(dataBaseParam = dataSourceInfo.getDataBaseParam().trim()).startsWith("?")) {
            dataBaseParam = "?" + dataBaseParam;
        }
        return "jdbc:mysql://" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getDataBaseName() + dataBaseParam;
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        return sql + " limit " + (pageNumber - 1) * pageSize + "," + pageSize;
    }
}

