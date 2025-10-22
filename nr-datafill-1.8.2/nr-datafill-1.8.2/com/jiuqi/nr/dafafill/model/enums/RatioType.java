/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum RatioType {
    NONE(1, "\u65e0"),
    PERCENT(2, "\u767e\u5206\u6bd4"),
    PERMIL(3, "\u5343\u5206\u6bd4");

    private int value;
    private String title;

    private RatioType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static RatioType valueOf(int value) {
        for (RatioType type : RatioType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

