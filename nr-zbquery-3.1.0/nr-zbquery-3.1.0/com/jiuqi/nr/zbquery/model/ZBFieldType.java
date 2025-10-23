/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum ZBFieldType {
    FIXED(1, "\u56fa\u5b9a\u6307\u6807"),
    DETAIL(4, "\u660e\u7ec6\u6307\u6807");

    private int value;
    private String title;

    private ZBFieldType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ZBFieldType valueOf(int value) {
        for (ZBFieldType type : ZBFieldType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

