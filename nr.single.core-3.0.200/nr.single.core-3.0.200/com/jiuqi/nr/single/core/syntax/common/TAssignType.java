/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum TAssignType {
    ASSIGN_TABLE(0);

    private int intValue;
    private static HashMap<Integer, TAssignType> mappings;

    private static synchronized HashMap<Integer, TAssignType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TAssignType(int value) {
        this.intValue = value;
        TAssignType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TAssignType forValue(int value) {
        return TAssignType.getMappings().get(value);
    }
}

