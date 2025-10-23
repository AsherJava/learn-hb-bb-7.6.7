/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum MagnitudeType {
    NOTDIMESSION(1L, "\u4e0d\u8bbe\u7f6e\u91cf\u7eb2"),
    AMOUNT(2L, "\u91d1\u989d");

    private long value;
    private String title;

    private MagnitudeType(long value, String title) {
        this.value = value;
        this.title = title;
    }

    public long value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static MagnitudeType valueOf(int value) {
        for (MagnitudeType type : MagnitudeType.values()) {
            if ((long)value != type.value()) continue;
            return type;
        }
        return null;
    }
}

