/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.variant;

public enum VarType {
    NULL(-1),
    STRING(6),
    DOUBLE(3),
    INTEGER(5),
    LONG(8),
    DATETIME(2),
    BOOLEAN(1),
    BIGDECIMAL(10);

    private int value;

    private VarType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static VarType of(int varType) throws IllegalArgumentException {
        for (VarType value : VarType.values()) {
            if (value.value() != varType) continue;
            return value;
        }
        throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u53d8\u91cf\u7c7b\u578b\u503c\uff1a" + varType);
    }
}

