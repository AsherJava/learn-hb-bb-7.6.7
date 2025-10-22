/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum TableSample {
    NOTSUPPORTED(1, "\u4e0d\u652f\u6301"),
    PERIODUNITZB(2, "\u65f6\u671f\u5355\u4f4d\u6307\u6807"),
    UNITPERIODZB(3, "\u5355\u4f4d\u65f6\u671f\u6307\u6807"),
    PERIODZBUNIT(4, "\u65f6\u671f\u6307\u6807\u5355\u4f4d"),
    UNITZBPERIOD(5, "\u5355\u4f4d\u6307\u6807\u65f6\u671f");

    private int value;
    private String title;

    private TableSample(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static TableSample valueOf(int value) {
        for (TableSample type : TableSample.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

