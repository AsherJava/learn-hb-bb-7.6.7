/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.common;

import java.util.List;

public class SqlUtil {
    public static String buildInSql(String field, List<String> values) {
        StringBuffer sql = new StringBuffer();
        sql.append(field).append(" in ( ");
        StringBuffer inSql = new StringBuffer();
        for (String recid : values) {
            if (inSql.length() > 0) {
                inSql.append(",");
            }
            inSql.append("'").append(recid).append("'");
        }
        sql.append(inSql).append(" )\n");
        return sql.toString();
    }
}

