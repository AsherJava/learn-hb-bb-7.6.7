/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

public enum FlowsType {
    DEFAULT(0),
    NOSTARTUP(1),
    WORKFLOW(2),
    EXTEND(3);

    private final int value;
    private static FlowsType[] TYPES;

    private FlowsType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FlowsType fromType(int type) {
        return TYPES[type];
    }

    static {
        TYPES = new FlowsType[]{DEFAULT, NOSTARTUP, WORKFLOW, EXTEND};
    }
}

