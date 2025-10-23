/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.cell;

@Deprecated
public enum SummaryMode {
    NONE(0, "\u65e0"),
    ADD(1, "\u7d2f\u52a0"),
    COUNT(2, "\u8ba1\u6570"),
    MAX(3, "\u6700\u5927\u503c"),
    MIN(4, "\u6700\u5c0f\u503c"),
    AVG(5, "\u5e73\u5747\u503c"),
    REPEAT(99, "\u91cd\u590d\u8ba1\u6570");

    private int value;
    private String title;

    private SummaryMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SummaryMode valueOf(int value) {
        for (SummaryMode type : SummaryMode.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

