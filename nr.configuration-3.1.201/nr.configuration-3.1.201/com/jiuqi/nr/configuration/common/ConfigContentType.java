/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.common;

import java.util.HashMap;

public enum ConfigContentType {
    CCT_TEXT(0),
    CCT_XML(1),
    CCT_JSON(2),
    CCT_BINARY(3),
    CCT_HTML(4),
    CCT_RTF(5);

    private int intValue;
    private static HashMap<Integer, ConfigContentType> mappings;

    private static synchronized HashMap<Integer, ConfigContentType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private ConfigContentType(int value) {
        this.intValue = value;
        ConfigContentType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static ConfigContentType forValue(int value) {
        return ConfigContentType.getMappings().get(value);
    }
}

