/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum StackDataType {
    SD_REAL_TYPE(0),
    SD_STRING_TYPE(1),
    SD_DATE_TYPE(2),
    SD_TEXT_TYPE(3);

    private int intValue;
    private static HashMap<Integer, StackDataType> mappings;

    private static synchronized HashMap<Integer, StackDataType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private StackDataType(int value) {
        this.intValue = value;
        StackDataType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static StackDataType forValue(int value) {
        return StackDataType.getMappings().get(value);
    }
}

