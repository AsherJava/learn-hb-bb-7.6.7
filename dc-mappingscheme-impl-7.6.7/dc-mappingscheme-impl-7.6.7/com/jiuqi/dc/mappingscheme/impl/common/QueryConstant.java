/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.common;

public class QueryConstant {
    public static final String INSERT_SQL_REGEX = "[\\s\\S]*insert\\s+into\\s+[\\s\\S]*values[\\s\\S]*";
    public static final String UPDATE_SQL_REGEX = "[\\s\\S]*update\\s+[\\s\\S]+set\\s+[\\s\\S]*";
    public static final String MERGE_SQL_REGEX = "[\\s\\S]*merge\\s+into\\s+[\\s\\S]*";
    public static final String DELETE_SQL_REGEX = "[\\s\\S]*delete\\s+from\\s+[\\s\\S]*";
    public static final String TRUNCATE_SQL_REGEX = "[\\s\\S]*truncate\\s+table\\s+[\\s\\S]*";
    public static final String CREATE_SQL_REGEX = "[\\s\\S]*create\\s+((table+)|(user+)|(tablespace+)|(database+)|([\\s\\S]*index+))\\s+[\\s\\S]*";
    public static final String ALTER_SQL_REGEX = "[\\s\\S]*alter\\s+table\\s+[\\s\\S]*";
    public static final String DROP_SQL_REGEX = "[\\s\\S]*drop\\s+((table+)|(user+)|(tablespace+)|(database+)|(index+))\\s+[\\s\\S]*";
}

