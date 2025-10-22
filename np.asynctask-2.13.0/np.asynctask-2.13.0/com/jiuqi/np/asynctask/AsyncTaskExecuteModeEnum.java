/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

public enum AsyncTaskExecuteModeEnum {
    IMMEDIATELY("immediately", "\u7acb\u5373\u6267\u884c"),
    DISPATCH("dispatch", "\u8c03\u5ea6\u6267\u884c");

    private final String value;
    private final String title;

    private AsyncTaskExecuteModeEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

