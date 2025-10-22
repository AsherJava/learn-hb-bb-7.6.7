/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.compiler.type;

import java.util.HashMap;

public enum TPSParameterMode {
    PM_IN(0),
    PM_OUT(1),
    PM_IN_OUT(2);

    private int intValue;
    private static HashMap<Integer, TPSParameterMode> mappings;

    private static synchronized HashMap<Integer, TPSParameterMode> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TPSParameterMode(int value) {
        this.intValue = value;
        TPSParameterMode.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TPSParameterMode forValue(int value) {
        return TPSParameterMode.getMappings().get(value);
    }
}

