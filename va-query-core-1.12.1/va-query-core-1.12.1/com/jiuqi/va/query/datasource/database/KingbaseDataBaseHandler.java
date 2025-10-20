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

public class KingbaseDataBaseHandler
extends AbstractQueryDataBaseHandler {
    public KingbaseDataBaseHandler() {
        super("KING_BASE", "kingbase(\u4eba\u5927\u91d1\u4ed3)", "KingbaseES", "com.kingbase8.Driver");
    }

    public String getCreateTempTableSql() {
        return "CREATE TABLE DC_QUERY_TEMPTABLE\n(  \nQUERYKEY  VARCHAR(120), \n" + ParamTypeEnum.STRING.getTempTableField() + "   VARCHAR(120), \n" + ParamTypeEnum.INTEGER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.NUMBER.getTempTableField() + "   DECIMAL(23),  \n" + ParamTypeEnum.BOOL.getTempTableField() + "   DECIMAL(1),  \n" + ParamTypeEnum.DATE.getTempTableField() + "   DATE,  \n" + ParamTypeEnum.DATE_TIME.getTempTableField() + "   TIMESTAMP  \n);  \n";
    }

    public String getUrl(DataSourceInfoVO dataSourceInfo) {
        return "jdbc:kingbase8://" + dataSourceInfo.getIp() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getDataBaseName();
    }

    public String getPageSql(String sql, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        int end = pageNumber * pageSize;
        if (start == 0) {
            return String.format(" select tmp.* from ( %1$s ) tmp where rownum <= " + end, sql);
        }
        String pageSql = " select * from (select tmp.*, rownum rn from ( %1$s ) tmp %2$s ) %3$s";
        return String.format(pageSql, sql, " where rownum <= " + end, " where rn > " + start);
    }
}

