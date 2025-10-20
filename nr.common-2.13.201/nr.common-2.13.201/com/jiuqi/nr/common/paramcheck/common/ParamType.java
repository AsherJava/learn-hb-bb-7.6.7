/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.common;

import java.util.HashMap;

public enum ParamType {
    CUSTOM_PARAM(0),
    SYSTEM_PARAM(1),
    TASK_PARAM(2),
    NR_PARAM(3),
    FORMSCHEME_PARAM(4);

    private int intValue;
    private static HashMap<Integer, ParamType> mappings;

    private static synchronized HashMap<Integer, ParamType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private ParamType(int value) {
        this.intValue = value;
        ParamType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static ParamType forValue(int value) {
        return ParamType.getMappings().get(value);
    }
}

