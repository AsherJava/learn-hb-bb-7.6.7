/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum SelectType {
    NONE(-1, "\u65e0"),
    SINGLE(1, "\u5355\u9009"),
    MULTIPLE(2, "\u591a\u9009"),
    RANGE(3, "\u8303\u56f4");

    private int value;
    private String title;

    private SelectType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SelectType valueOf(int value) {
        for (SelectType type : SelectType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

