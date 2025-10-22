/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum TypeType {
    NIL_TYPE(0),
    INT_TYPE(1),
    REAL_TYPE(2),
    STR_TYPE(3),
    BOOL_TYPE(4),
    TABLE_TYPE(5),
    CODE_TYPE(6),
    EXIST_TYPE(7),
    BZZ_TYPE(8);

    private int intValue;
    private static HashMap<Integer, TypeType> mappings;

    private static synchronized HashMap<Integer, TypeType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TypeType(int value) {
        this.intValue = value;
        TypeType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TypeType forValue(int value) {
        return TypeType.getMappings().get(value);
    }
}

