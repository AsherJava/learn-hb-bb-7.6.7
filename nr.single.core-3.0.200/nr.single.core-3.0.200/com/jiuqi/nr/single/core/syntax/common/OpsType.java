/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum OpsType {
    SY_PLUS(0),
    SY_MINUS(1),
    SY_PRODUCT(2),
    SY_DIV(3),
    SY_CPLUS(4),
    SY_EQU(5),
    SY_NEQ(6),
    SY_GLT(7),
    SY_BLT(8),
    SY_GLE(9),
    SY_BLE(10),
    SY_AND(11),
    SY_OR(12),
    SY_ASSIGN_VALUE(13),
    YS_IN(14);

    private int intValue;
    private static HashMap<Integer, OpsType> mappings;

    private static synchronized HashMap<Integer, OpsType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private OpsType(int value) {
        this.intValue = value;
        OpsType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static OpsType forValue(int value) {
        return OpsType.getMappings().get(value);
    }
}

