/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public enum OperationType {
    ADD("\u65b0\u589e"),
    UPDATE("\u4fee\u6539"),
    REMOVE("\u5220\u9664"),
    STOP("\u505c\u7528"),
    ENABLE("\u542f\u7528"),
    UP("\u4e0a\u79fb"),
    DOWN("\u4e0b\u79fb"),
    SORT("\u6392\u5e8f"),
    IMPORT("\u5bfc\u5165");

    private final String title;

    private OperationType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

