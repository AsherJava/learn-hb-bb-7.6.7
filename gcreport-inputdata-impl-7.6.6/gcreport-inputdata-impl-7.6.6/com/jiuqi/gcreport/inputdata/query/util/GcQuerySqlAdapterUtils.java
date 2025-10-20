/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.query.util;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.util.StringUtils;
import java.util.Date;

public class GcQuerySqlAdapterUtils {
    public static String getDateCompareSql(QueryParam queryParam, String alias, String fieldName, String compareOper, Date date) {
        StringBuilder sql = new StringBuilder();
        if (StringUtils.isNotEmpty((String)alias)) {
            sql.append(alias).append(".");
            sql.append(fieldName);
            sql.append(compareOper);
            try {
                IDatabase database = queryParam.getDatabase();
                if (!"mysql".equalsIgnoreCase(database.getName())) {
                    sql.append(database.createSQLInterpretor(queryParam.getConnection()).formatSQLDate(date));
                } else {
                    sql.append("'").append(DateUtils.format((Date)date, (String)"yyyy-MM-dd")).append("' ");
                }
            }
            catch (SQLInterpretException e) {
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.sqlinterpretexceptionmsg"), e);
            }
            finally {
                queryParam.closeConnection();
            }
        }
        return sql.toString();
    }
}

