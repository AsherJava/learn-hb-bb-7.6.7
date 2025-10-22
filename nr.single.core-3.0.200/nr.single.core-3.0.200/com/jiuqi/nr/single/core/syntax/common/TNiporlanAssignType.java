/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum TNiporlanAssignType {
    NA_TABLE(0);

    private int intValue;
    private static HashMap<Integer, TNiporlanAssignType> mappings;

    private static synchronized HashMap<Integer, TNiporlanAssignType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TNiporlanAssignType(int value) {
        this.intValue = value;
        TNiporlanAssignType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TNiporlanAssignType forValue(int value) {
        return TNiporlanAssignType.getMappings().get(value);
    }
}

