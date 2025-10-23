/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

public enum GlobalType {
    ALL("0", "\u5168\u90e8\u4efb\u52a1"),
    EXIST("1", "\u5b58\u5728\u65b9\u6848\u7684\u4efb\u52a1");

    private String value;
    private String title;

    private GlobalType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }
}

