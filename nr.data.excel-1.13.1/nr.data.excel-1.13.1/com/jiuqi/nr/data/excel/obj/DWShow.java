/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

public enum DWShow {
    TITLE("0"),
    CODE("1"),
    PERIOD_TITLE("2"),
    TASK_TITLE("3");

    private final String key;

    private DWShow(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}

