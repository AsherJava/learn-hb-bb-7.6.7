/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.common;

public enum AsyncTaskPoolType {
    ASYNC_TASK_CAL_ZB("ASYNC_TASK_CAL_ZB", "\u5904\u7406\u8ba1\u7b97\u6307\u6807");

    private final String id;
    private final String title;

    private AsyncTaskPoolType(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }
}

