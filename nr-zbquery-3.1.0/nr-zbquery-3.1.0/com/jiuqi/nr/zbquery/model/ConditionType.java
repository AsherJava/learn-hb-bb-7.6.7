/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum ConditionType {
    SINGLE(1, "\u5355\u9009"),
    MULTIPLE(2, "\u591a\u9009"),
    RANGE(3, "\u8303\u56f4"),
    MATCH(4, "\u5339\u914d");

    private int value;
    private String title;

    private ConditionType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ConditionType valueOf(int value) {
        for (ConditionType type : ConditionType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

