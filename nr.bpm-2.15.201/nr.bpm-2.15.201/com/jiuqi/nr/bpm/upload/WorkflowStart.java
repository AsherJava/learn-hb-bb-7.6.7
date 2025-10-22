/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.upload;

public enum WorkflowStart {
    NOSTART(1),
    START(0);

    private final int value;
    private static WorkflowStart[] TYPES;

    private WorkflowStart(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WorkflowStart fromType(int type) {
        return TYPES[type];
    }

    static {
        TYPES = new WorkflowStart[]{NOSTART, START};
    }
}

