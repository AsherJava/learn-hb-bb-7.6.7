/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.enums;

public enum DataHandleState {
    FAILURE("\u5931\u8d25"),
    SUCCESS("\u6210\u529f"),
    UNEXECUTE("\u672a\u5904\u7406"),
    EXECUTING("\u6267\u884c\u4e2d"),
    CANCELED("\u5df2\u53d6\u6d88");

    private String title;

    private DataHandleState(String title) {
        this.title = title;
    }

    public int getState() {
        return this.ordinal();
    }

    public String getTitle() {
        return this.title;
    }

    public static DataHandleState typeof(Integer type) {
        for (DataHandleState executeState : DataHandleState.values()) {
            if (executeState.getState() != type.intValue()) continue;
            return executeState;
        }
        return null;
    }
}

