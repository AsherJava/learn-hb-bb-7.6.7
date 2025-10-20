/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.database.AbstractQueryDataBaseHandler
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 */
package com.jiuqi.bde.bizmodel.define.adaptor.database;

import com.jiuqi.va.query.datasource.database.AbstractQueryDataBaseHandler;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OscarDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public OscarDataBaseHandler() {
        super("OSCAR", "\u795e\u901a\u6570\u636e\u5e93", "OSCAR", "com.oscar.Driver");
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        String dataBaseParam = "";
        if (StringUtils.hasText(dataSourceInfo.getDataBaseParam()) && !(dataBaseParam = dataSourceInfo.getDataBaseParam().trim()).startsWith("?")) {
            dataBaseParam = "?" + dataBaseParam;
        }
        return "jdbc:oscar://" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getDataBaseName() + dataBaseParam;
    }

    public String getCreateTempTableSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE ").append("DC_QUERY_TEMPTABLE").append("\n");
        sql.append("(  \n");
        sql.append("QUERYKEY  VARCHAR(120), \n");
        sql.append(ParamTypeEnum.STRING.getTempTableField()).append("   VARCHAR(200), \n");
        sql.append(ParamTypeEnum.INTEGER.getTempTableField()).append("   NUMBER(23),  \n");
        sql.append(ParamTypeEnum.NUMBER.getTempTableField()).append("   NUMBER(23),  \n");
        sql.append(ParamTypeEnum.BOOL.getTempTableField()).append("   BOOLEAN,  \n");
        sql.append(ParamTypeEnum.DATE.getTempTableField()).append("   DATE,  \n");
        sql.append(ParamTypeEnum.DATE_TIME.getTempTableField()).append("   TIMESTAMP  \n");
        sql.append(")  \n");
        return sql.toString();
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        String template = "SELECT * FROM ( %1$s ) AS inner1_ LIMIT %2$s OFFSET %3$s";
        return String.format(template, sql, pageSize, (pageNumber - 1) * pageSize);
    }
}

