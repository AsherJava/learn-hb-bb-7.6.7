/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.upload;

public enum WorkflowStatus {
    NOSTARTUP(0),
    DEFAULT(1),
    WORKFLOW(2);

    private final int value;
    private static WorkflowStatus[] TYPES;

    private WorkflowStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WorkflowStatus fromType(int type) {
        return TYPES[type];
    }

    static {
        TYPES = new WorkflowStatus[]{DEFAULT, NOSTARTUP, WORKFLOW};
    }
}

