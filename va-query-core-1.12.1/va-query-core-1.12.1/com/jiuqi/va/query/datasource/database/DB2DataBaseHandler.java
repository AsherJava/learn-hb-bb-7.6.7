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
public class DB2DataBaseHandler
extends AbstractQueryDataBaseHandler {
    public DB2DataBaseHandler() {
        super("DB2", "DB2", "DB2", "com.ibm.db2.jcc.DB2Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  VARCHAR(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   VARCHAR(120), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   DECIMAL(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n);  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return String.format("jdbc:db2://%1$s:%2$s/%3$s", dataSourceInfo.getIp(), dataSourceInfo.getPort(), dataSourceInfo.getDataBaseName());
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        String template = "select * from ( select inner2_.*, rownumber() over() as rownumber_ from ( %1$s ) as inner2_ ) as inner1_ where rownumber_ > %2$s fetch first %3$s rows only";
        return String.format(template, sql, (pageNumber - 1) * pageSize, pageSize);
    }
}

