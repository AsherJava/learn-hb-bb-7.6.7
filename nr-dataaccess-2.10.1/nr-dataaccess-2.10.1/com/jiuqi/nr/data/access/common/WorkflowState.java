/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

import java.util.HashMap;

public enum WorkflowState {
    ORIGINAL_UPLOAD(0),
    SUBMITED(1),
    UPLOADED(2),
    CONFIRMED(3),
    RETURNED(4),
    REJECTED(5);

    private int intValue;
    private static HashMap<Integer, WorkflowState> mappings;

    private static synchronized HashMap<Integer, WorkflowState> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private WorkflowState(int value) {
        this.intValue = value;
        WorkflowState.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static WorkflowState forValue(int value) {
        return WorkflowState.getMappings().get(value);
    }
}

