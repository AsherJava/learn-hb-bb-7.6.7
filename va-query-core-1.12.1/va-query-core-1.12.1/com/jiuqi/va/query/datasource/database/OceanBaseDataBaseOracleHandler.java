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
public class OceanBaseDataBaseOracleHandler
extends AbstractQueryDataBaseHandler {
    public static final String OCEAN_BASE = "OceanBase_Oracle";

    public OceanBaseDataBaseOracleHandler() {
        super(OCEAN_BASE, "oceanbase(oracle\u6a21\u5f0f)", OCEAN_BASE, "com.oceanbase.jdbc.Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE GLOBAL TEMPORARY TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  NVARCHAR2(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   NVARCHAR2(200), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   NUMBER(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   NUMBER(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   NUMBER(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n)  \nON COMMIT PRESERVE ROWS ;  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return String.format("jdbc:oceanbase://%1$s:%2$s/%3$s", dataSourceInfo.getIp(), dataSourceInfo.getPort(), dataSourceInfo.getDataBaseName());
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

