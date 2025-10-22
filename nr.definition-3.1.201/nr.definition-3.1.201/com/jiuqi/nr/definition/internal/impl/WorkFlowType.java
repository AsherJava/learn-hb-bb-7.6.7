/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

public enum WorkFlowType {
    ENTITY(2),
    FORM(3),
    GROUP(4);

    private final int value;
    private static WorkFlowType[] TYPES;

    private WorkFlowType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WorkFlowType fromType(int type) {
        return TYPES[type - 2];
    }

    static {
        TYPES = new WorkFlowType[]{ENTITY, FORM, GROUP};
    }
}

