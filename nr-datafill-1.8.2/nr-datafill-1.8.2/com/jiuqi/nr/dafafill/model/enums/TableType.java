/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum TableType {
    FIXED(1, "\u56fa\u5b9a\u8868"),
    FLOAT(2, "\u6d6e\u52a8\u8868"),
    MASTER(3, "\u4e3b\u6570\u636e\u8868"),
    ACCOUNT(4, "\u53f0\u8d26\u8868"),
    FMDM(5, "\u65b0-\u5c01\u9762\u4ee3\u7801");

    private int value;
    private String title;

    private TableType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static TableType valueOf(int value) {
        for (TableType type : TableType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

