/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.common;

import java.util.HashMap;

public enum ExtendAttribute {
    SIZE(0),
    TYPE(1),
    FIXE_SIZE(2),
    DIGIT(3),
    REFER(4),
    ORDER(5),
    ENABLE_NULL(6),
    UNIQUE(7),
    FORMAT(8),
    VALUE_TYPE(9),
    CALCULATION(10);

    private int intValue;
    private static HashMap<Integer, ExtendAttribute> mappings;

    private static synchronized HashMap<Integer, ExtendAttribute> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private ExtendAttribute(int value) {
        this.intValue = value;
        ExtendAttribute.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static ExtendAttribute forValue(int value) {
        return ExtendAttribute.getMappings().get(value);
    }
}

