/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TaskLinkMatchingType {
    MATCHING_TYPE_CODE(0),
    MATCHING_TYPE_TITLE(1),
    FORM_TYPE_EXPRESSION(2),
    MATCHING_TYPE_PRIMARYKEY(3);

    private int intValue;
    private static Map<Integer, TaskLinkMatchingType> mappings;

    private static Map<Integer, TaskLinkMatchingType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(TaskLinkMatchingType.values()).collect(Collectors.toMap(TaskLinkMatchingType::getValue, f -> f));
        }
        return mappings;
    }

    private TaskLinkMatchingType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static TaskLinkMatchingType forValue(int value) {
        return TaskLinkMatchingType.getMappings().get(value);
    }
}

