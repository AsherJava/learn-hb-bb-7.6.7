/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.state.common;

public class StateTableConstant {
    public static final String TABLE_PREFIX = "SYS_STATE_";
    public static final String SPLIT_CHAR = ";";
    public static final String STATE_STATE = "S_STATE";
    public static final String STATE_CREATETETIME = "CREATETETIME";
    public static final String STATE_USERID = "S_USERID";

    public static String getStateTableName(String taskCode) {
        return StateTableConstant.getTableName(taskCode, TABLE_PREFIX);
    }

    private static String getTableName(String taskCode, String tableNamePrefix) {
        return String.format("%s%s", tableNamePrefix, taskCode);
    }
}

