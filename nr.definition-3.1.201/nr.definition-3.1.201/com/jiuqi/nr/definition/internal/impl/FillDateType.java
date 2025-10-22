/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

public enum FillDateType {
    NONE(0),
    NATURAL_DAY(1),
    WORK_DAY(2);

    private final int value;
    private static FillDateType[] TYPES;

    private FillDateType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FillDateType fromType(int type) {
        return TYPES[type];
    }

    static {
        TYPES = new FillDateType[]{NONE, NATURAL_DAY, WORK_DAY};
    }
}

