/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TaskGatherType {
    TASK_GATHER_MANUAL(0),
    TASK_GATHER_AUTO(1);

    private int intValue;
    private static Map<Integer, TaskGatherType> mappings;

    private static Map<Integer, TaskGatherType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TaskGatherType.values()).collect(Collectors.toMap(TaskGatherType::getValue, f -> f));
        }
        return mappings;
    }

    private TaskGatherType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TaskGatherType forValue(int value) {
        return TaskGatherType.getMappings().get(value);
    }
}

