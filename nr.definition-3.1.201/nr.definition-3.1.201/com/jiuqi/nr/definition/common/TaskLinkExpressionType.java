/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TaskLinkExpressionType {
    EQUALS(0),
    INCLUDE(1),
    BEGIN_WITH(2),
    END_WITH(3);

    private int intValue;
    private static Map<Integer, TaskLinkExpressionType> mappings;

    private static Map<Integer, TaskLinkExpressionType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TaskLinkExpressionType.values()).collect(Collectors.toMap(TaskLinkExpressionType::getValue, f -> f));
        }
        return mappings;
    }

    private TaskLinkExpressionType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TaskLinkExpressionType forValue(int value) {
        return TaskLinkExpressionType.getMappings().get(value);
    }
}

