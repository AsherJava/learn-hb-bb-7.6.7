/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

import java.util.regex.Pattern;

public final class QueryConstant {
    public static final String L_EXPORT = "export";
    public static final String L_DATA = "data";
    public static final String L_V = "v";
    public static final String KEY_TAG_PRINT = "tagPrint";
    public static final String PARAM_REG_START_STR = "@";
    public static final String PARAM_REG_END_STR = "";
    public static final String PARAM_START_STR = "@";
    public static final String PARAM_END_STR = "}";
    public static final String PARAM_REPLACE_CHR = "#";
    public static final String INSERT_SQL_REGEX = "[\\s\\S]*insert\\s+into\\s+[\\s\\S]*values[\\s\\S]*";
    public static final String UPDATE_SQL_REGEX = "[\\s\\S]*update\\s+[\\s\\S]+set\\s+[\\s\\S]*";
    public static final String MERGE_SQL_REGEX = "[\\s\\S]*merge\\s+into\\s+[\\s\\S]*";
    public static final String DELETE_SQL_REGEX = "[\\s\\S]*delete\\s+from\\s+[\\s\\S]*";
    public static final String TRUNCATE_SQL_REGEX = "[\\s\\S]*truncate\\s+table\\s+[\\s\\S]*";
    public static final String CREATE_SQL_REGEX = "[\\s\\S]*create\\s+((table+)|(user+)|(tablespace+)|(database+)|([\\s\\S]*index+))\\s+[\\s\\S]*";
    public static final String ALTER_SQL_REGEX = "[\\s\\S]*alter\\s+table\\s+[\\s\\S]*";
    public static final String DROP_SQL_REGEX = "[\\s\\S]*drop\\s+((table+)|(user+)|(tablespace+)|(database+)|(index+))\\s+[\\s\\S]*";
    public static final String FIELD_TITLE_SEPARATOR = "|";
    public static final Pattern VARIABLE_PARAM_PATTERN = Pattern.compile("#(\\w+)#");
    public static final int IN_PARAM_VALUE_MAX_AMOUNT = 10;
    public static final String TEMP_TABLE_NAME = "DC_QUERY_TEMPTABLE";
    public static final String QUERY_TEMPLATE_IMPORT_SKIP = "skip";
    public static final String QUERY_TEMPLATE_IMPORT_COVER = "cover";
    public static final Pattern PATTERN_ONE = Pattern.compile("#.*?#");
    public static final int EXPORT_FETCH_SIZE = 50000;
    public static final String U_ORIGIN_DATA_MAP = "__ORIGIN_DATA_MAP__";
    public static final String L_SORT_ASC = "asc";
    public static final String L_SORT_DESC = "desc";

    private QueryConstant() {
    }
}

