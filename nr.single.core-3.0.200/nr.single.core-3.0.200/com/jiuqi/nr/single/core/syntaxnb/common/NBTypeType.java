/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.common;

import java.util.HashMap;

public enum NBTypeType {
    NIL_TYPE(0),
    INT_TYPE(1),
    REAL_TYPE(2),
    STR_TYPE(3),
    BOOL_TYPE(4),
    DATE_TYPE(5),
    SET_TYPE(6),
    TABLE_TYPE(7),
    CODE_TYPE(8),
    FINANCE_TYPE(9),
    TEXT_TYPE(10);

    private int intValue;
    private static HashMap<Integer, NBTypeType> mappings;

    private static synchronized HashMap<Integer, NBTypeType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private NBTypeType(int value) {
        this.intValue = value;
        NBTypeType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static NBTypeType forValue(int value) {
        return NBTypeType.getMappings().get(value);
    }
}

