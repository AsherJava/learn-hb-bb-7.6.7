/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

public enum PeriodType {
    ONCE(0, "\u4e00\u6b21\u6027"),
    HOUR(1, "\u6bcf\u5c0f\u65f6"),
    DAY(2, "\u6bcf\u5929"),
    WEEK(3, "\u6bcf\u5468"),
    MONTH(4, "\u6bcf\u6708"),
    SEASON(5, "\u6bcf\u5b63"),
    YEAR(6, "\u6bcf\u5e74"),
    MINUTE(16, "\u5206\u949f");

    private int value;
    private String title;

    private PeriodType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

