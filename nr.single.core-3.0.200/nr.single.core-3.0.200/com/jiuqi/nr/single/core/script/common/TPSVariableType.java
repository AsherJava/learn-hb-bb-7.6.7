/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.common;

import java.util.HashMap;

public enum TPSVariableType {
    IVT_GLOBAL(0),
    IVT_PARAM(1),
    IVT_VARIABLE(2);

    private int intValue;
    private static HashMap<Integer, TPSVariableType> mappings;

    private static synchronized HashMap<Integer, TPSVariableType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TPSVariableType(int value) {
        this.intValue = value;
        TPSVariableType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TPSVariableType forValue(int value) {
        return TPSVariableType.getMappings().get(value);
    }
}

