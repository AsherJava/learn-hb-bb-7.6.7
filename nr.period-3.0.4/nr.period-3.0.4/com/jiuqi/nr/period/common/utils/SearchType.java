/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum SearchType {
    ALL(0),
    DEFAULT(1),
    CUSTOM(2),
    PERIOD13(3);

    private int intValue;
    private static Map<Integer, SearchType> mappings;

    private static Map<Integer, SearchType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(SearchType.values()).collect(Collectors.toMap(SearchType::getValue, f -> f));
        }
        return mappings;
    }

    private SearchType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static SearchType forValue(int value) {
        return SearchType.getMappings().get(value);
    }
}

