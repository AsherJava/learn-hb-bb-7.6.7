/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TaskType {
    TASK_TYPE_DEFAULT(0),
    TASK_TYPE_ANALYSIS(1),
    TASK_TYPE_SURVEY(2);

    private int value;
    private static Map<Integer, TaskType> mappings;

    private static Map<Integer, TaskType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TaskType.values()).collect(Collectors.toMap(TaskType::getValue, f -> f));
        }
        return mappings;
    }

    private TaskType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static TaskType forValue(int value) {
        return TaskType.getMappings().get(value);
    }
}

