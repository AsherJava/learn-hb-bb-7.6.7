/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum EntityCacheMode {
    ENTITY_CACHE_NORMAL(0),
    ENTITY_CACHE_LEAST(1),
    ENTITY_CACHE_COMPLETE(2);

    private int intValue;
    private static Map<Integer, EntityCacheMode> mappings;

    private static Map<Integer, EntityCacheMode> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(EntityCacheMode.values()).collect(Collectors.toMap(EntityCacheMode::getValue, f -> f));
        }
        return mappings;
    }

    private EntityCacheMode(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static EntityCacheMode forValue(int value) {
        return EntityCacheMode.getMappings().get(value);
    }
}

