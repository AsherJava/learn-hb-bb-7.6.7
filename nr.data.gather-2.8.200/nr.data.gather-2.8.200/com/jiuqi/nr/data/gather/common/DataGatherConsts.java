/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.common;

public class DataGatherConsts {
    public static final String NODE_GATHER_TASK = "NODE_GATHER_TASK";
    public static final String SELECT_GATHER_TASK = "SELECT_GATHER_TASK";
    public static final String NODE_CHECK_TASK = "NODE_CHECK_TASK";
    public static final String MOUDLE = "\u6c47\u603b\u670d\u52a1";
    public static final String NODE_CHECK_TOLERANCE = "NODE_CHECK_TOLERANCE";
    public static final String NR_GATHER_LOCK_TABLE = "NR_GATHER_LOCK_%s";
    public static final String NR_GATHER_LOCK_TITLE = "\u6c47\u603b\u9501\u5b9a\u72b6\u6001\u8868";
    public static final String LOCK_DATATABLE_KEY = "DATA_TABLE_KEY";
    public static final String LOCK_CREATE_TIME = "CREATE_TIME";
    public static final String LOCK_HEART_TIME = "HEART_TIME";
    public static final String LOCK_SERVER_NAME = "SERVER_NAME";

    public static String getLockTableName(String schemeCode) {
        return String.format(NR_GATHER_LOCK_TABLE, schemeCode);
    }

    public static String getLockTableTitle(String schemeCode) {
        return String.format("\u6570\u636e\u65b9\u6848\u3010%s\u3011%s", schemeCode, NR_GATHER_LOCK_TITLE);
    }
}

