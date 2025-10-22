/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum OrderMode {
    ASC(1, "\u5347\u5e8f"),
    DESC(2, "\u964d\u5e8f");

    private int value;
    private String title;

    private OrderMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static OrderMode valueOf(int value) {
        for (OrderMode type : OrderMode.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

