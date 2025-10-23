/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum MagnitudeValue {
    NONE(-1L, "\u9ed8\u8ba4"),
    ONE(1L, "\u5143"),
    HUNDRED(100L, "\u767e\u5143"),
    THOUSAND(1000L, "\u5343\u5143"),
    TENTHOUSAND(10000L, "\u4e07\u5143"),
    MILLION(1000000L, "\u767e\u4e07\u5143"),
    TENMILLION(10000000L, "\u5343\u4e07\u5143"),
    HUNDREDMILLION(100000000L, "\u4ebf\u5143"),
    TRILLION(1000000000000L, "\u4e07\u4ebf\u5143");

    private long value;
    private String title;

    private MagnitudeValue(long value, String title) {
        this.value = value;
        this.title = title;
    }

    public long value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static MagnitudeValue valueOf(int value) {
        for (MagnitudeValue type : MagnitudeValue.values()) {
            if ((long)value != type.value()) continue;
            return type;
        }
        return null;
    }
}

