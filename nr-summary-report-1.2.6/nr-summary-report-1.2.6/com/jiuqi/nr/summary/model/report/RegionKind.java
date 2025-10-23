/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

public enum RegionKind {
    FIX(0, "\u56fa\u5b9a"),
    FLOAT(1, "\u6d6e\u52a8");

    private int value;
    private String title;

    private RegionKind(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static RegionKind valueOf(int value) {
        for (RegionKind type : RegionKind.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

