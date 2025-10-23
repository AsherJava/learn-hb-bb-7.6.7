/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

public enum SchemeType {
    COMMON(0, "\u666e\u901a\u65b9\u6848"),
    FLOW(1, "\u6d41\u7a0b\u65b9\u6848");

    private final int value;
    private final String title;

    private SchemeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SchemeType fromValue(int value) {
        for (SchemeType myEnum : SchemeType.values()) {
            if (myEnum.value != value) continue;
            return myEnum;
        }
        return null;
    }
}

