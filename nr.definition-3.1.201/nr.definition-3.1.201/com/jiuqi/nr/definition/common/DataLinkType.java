/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DataLinkType {
    DATA_LINK_TYPE_FIELD(1),
    DATA_LINK_TYPE_FORMULA(2),
    DATA_LINK_TYPE_FMDM(3),
    DATA_LINK_TYPE_INFO(4);

    private int intValue;
    private static Map<Integer, DataLinkType> mappings;

    private static Map<Integer, DataLinkType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(DataLinkType.values()).collect(Collectors.toMap(DataLinkType::getValue, f -> f));
        }
        return mappings;
    }

    private DataLinkType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static DataLinkType forValue(int value) {
        return DataLinkType.getMappings().get(value);
    }
}

