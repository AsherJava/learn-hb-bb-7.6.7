/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.common;

public enum DynamicTempTableStatusEnum {
    All("all", "\u5168\u90e8\u72b6\u6001"),
    AVAILABLE("AVAILABLE", "\u53ef\u7528"),
    IN_USE("IN_USE", "\u4f7f\u7528\u4e2d");

    private final String status;
    private final String desc;

    private DynamicTempTableStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return this.status;
    }

    public String getDesc() {
        return this.desc;
    }
}

