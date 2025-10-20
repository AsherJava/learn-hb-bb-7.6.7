/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.List;

public class TableParseUtils {
    public static Boolean tableExist(String dataSourceCode, List<String> tableList) {
        if (CollectionUtils.isEmpty(tableList)) {
            return Boolean.FALSE;
        }
        GcBizJdbcTemplate gcBizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate(dataSourceCode);
        IDbSqlHandler dbSqlHandler = gcBizJdbcTemplate.getIDbSqlHandler();
        for (String tableName : tableList) {
            List columnList = gcBizJdbcTemplate.query(dbSqlHandler.getTableColumnSql(tableName), (rs, rowNum) -> rs.getString(1));
            if (!CollectionUtils.isEmpty(columnList)) continue;
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

