/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.enumeration;

public enum CurrencyType {
    ALL("\u5168\u90e8"),
    SUPERIOR("\u4e0a\u7ea7\u672c\u4f4d\u5e01"),
    SELF("\u672c\u4f4d\u5e01"),
    CUSTOM("\u81ea\u5b9a\u4e49");

    public final String title;

    private CurrencyType(String title) {
        this.title = title;
    }
}

