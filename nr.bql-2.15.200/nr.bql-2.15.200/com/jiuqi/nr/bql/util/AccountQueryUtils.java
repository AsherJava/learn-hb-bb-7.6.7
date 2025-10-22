/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.SqlQueryHelper
 */
package com.jiuqi.nr.bql.util;

import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.nr.bql.datasource.QueryContext;
import java.sql.Connection;
import java.sql.ResultSet;

public class AccountQueryUtils {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getAccountStartPeriod(QueryContext qContext, String accountTableName, Connection conn) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select min(t.").append("VALIDDATATIME").append(")");
        sql.append(" from ").append(accountTableName).append("_HIS").append(" t");
        try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
             ResultSet rs = sqlHelper.executeQuery(conn, sql.toString());){
            if (rs == null) return null;
            if (!rs.next()) return null;
            String string = rs.getString(1);
            return string;
        }
        catch (Exception e) {
            qContext.getLogger().error(e.getMessage(), (Throwable)e);
        }
        return null;
    }
}

