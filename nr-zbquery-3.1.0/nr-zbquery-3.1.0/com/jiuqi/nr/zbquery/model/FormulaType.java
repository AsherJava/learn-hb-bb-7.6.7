/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum FormulaType {
    PREYEAR(1, "\u4e0a\u5e74\u6570"),
    YOY(2, "\u540c\u6bd4"),
    PREPERIOD(3, "\u4e0a\u671f\u6570"),
    MOM(4, "\u73af\u6bd4"),
    CUSTOM(99, "\u81ea\u5b9a\u4e49");

    private int value;
    private String title;

    private FormulaType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static FormulaType valueOf(int value) {
        for (FormulaType type : FormulaType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

