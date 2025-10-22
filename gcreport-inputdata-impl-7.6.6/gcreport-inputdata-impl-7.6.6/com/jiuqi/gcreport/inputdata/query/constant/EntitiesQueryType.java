/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.query.constant;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.inputdata.query.base.GcQuerySqlParam;
import com.jiuqi.gcreport.inputdata.query.base.NrSqlFormatBuilder;

public enum EntitiesQueryType {
    INNERTABLE(1, "\u5185\u90e8\u5f55\u5165\u8868\u67e5\u8be2"){

        @Override
        public String formatSql(String sql, GcQuerySqlParam queryWhere, GcQuerySqlParam otherFilter) {
            NrSqlFormatBuilder sqlBuilder = new NrSqlFormatBuilder(sql);
            String formatSql = sqlBuilder.formatSql(queryWhere).replaceAll("\\s+([\\(])", "(");
            return sqlBuilder.replaceSqlCondition(formatSql);
        }
    }
    ,
    OFFSETTABLE(2, "\u62b5\u6d88\u5206\u5f55\u8868\u67e5\u8be2"){

        @Override
        public String formatSql(String sql, GcQuerySqlParam queryWhere, GcQuerySqlParam otherFilter) {
            NrSqlFormatBuilder sqlBuilder = new NrSqlFormatBuilder(sql);
            String formatSql = sqlBuilder.formatSql(otherFilter).replaceAll("\\s+([\\(])", "(");
            return sqlBuilder.replaceSqlCondition(formatSql);
        }
    }
    ,
    UNIONALL(3, "\u4e24\u8868\u6df7\u5408\u67e5\u8be2"){

        @Override
        public String formatSql(String sql, GcQuerySqlParam queryWhere, GcQuerySqlParam otherFilter) {
            NrSqlFormatBuilder sqlBuilder = new NrSqlFormatBuilder(sql);
            return sqlBuilder.formatUnionAllSql(queryWhere, otherFilter).replaceAll("\\s+([\\(])", "(");
        }
    };

    private String id;
    private int code;
    private String title;

    private EntitiesQueryType(int code, String title) {
        this.id = StringUtils.toViewString((Object)code);
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract String formatSql(String var1, GcQuerySqlParam var2, GcQuerySqlParam var3);

    public static EntitiesQueryType findOrgType(String type) {
        if (StringUtils.isEmpty((String)type)) {
            return null;
        }
        for (EntitiesQueryType itt : EntitiesQueryType.values()) {
            if (itt.name().equalsIgnoreCase(type)) {
                return itt;
            }
            if (type.equals(String.valueOf(itt.getCode()))) {
                return itt;
            }
            if (!type.equals(itt.getTitle())) continue;
            return itt;
        }
        return null;
    }
}

