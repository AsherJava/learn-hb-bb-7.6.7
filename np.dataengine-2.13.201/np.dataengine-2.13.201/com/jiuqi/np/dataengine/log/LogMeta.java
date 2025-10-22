/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.log;

public class LogMeta {
    private static final String LOG_TYPE = "LOG_TYPE";
    private static final String LOG_EXE_COST = "LOG_EXE_COST";
    private static final String LOG_TOTAL_COST = "LOG_TOTAL_COST";
    private static final String LOG_COL_COUNT = "LOG_COL_COUNT";
    private static final String LOG_ROW_COUNT = "LOG_ROW_COUNT";
    private static final String LOG_USER = "LOG_USER";
    private static final String LOG_TIME = "LOG_TIME";
    private static final String LOG_SQL = "LOG_SQL";

    public static String print() {
        StringBuilder buff = new StringBuilder();
        buff.append(LOG_TYPE).append(",");
        buff.append(LOG_EXE_COST).append(",");
        buff.append(LOG_TOTAL_COST).append(",");
        buff.append(LOG_COL_COUNT).append(",");
        buff.append(LOG_ROW_COUNT).append(",");
        buff.append(LOG_USER).append(",");
        buff.append(LOG_TIME).append(",");
        buff.append(LOG_SQL).append(",");
        return buff.toString();
    }
}

