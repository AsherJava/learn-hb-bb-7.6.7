/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.common;

import java.util.HashMap;

@Deprecated
public enum SystemOptionType {
    SYSTEM_OPTION(0),
    TASK_OPTION(1),
    FORMSCHEME_OPTION(2),
    ASYNCTASK_OPTION(3);

    private int intValue;
    private static HashMap<Integer, SystemOptionType> mappings;

    private static synchronized HashMap<Integer, SystemOptionType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private SystemOptionType(int value) {
        this.intValue = value;
        SystemOptionType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static SystemOptionType forValue(int value) {
        return SystemOptionType.getMappings().get(value);
    }
}

