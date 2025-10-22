/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.definition.option.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum ReportCacheOptionType {
    CACHE_EXPIRATION(0, "\u7f13\u5b58\u8fc7\u671f"),
    CACHE_PRELOAD(1, "\u7f13\u5b58\u9884\u70ed");

    private final int value;
    private final String title;
    private static final Map<Integer, ReportCacheOptionType> valueMap;

    private ReportCacheOptionType(int code, String title) {
        this.value = code;
        this.title = title;
    }

    public static ReportCacheOptionType valueOf(int value) {
        return valueMap.get(value);
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    @JsonCreator
    public ReportCacheOptionType fromValue(@JsonProperty(value="value") int value) {
        for (ReportCacheOptionType option : ReportCacheOptionType.values()) {
            if (option.getValue() != value) continue;
            return option;
        }
        return null;
    }

    public String getTitle() {
        return this.title;
    }

    static {
        valueMap = new HashMap<Integer, ReportCacheOptionType>();
        for (ReportCacheOptionType type : ReportCacheOptionType.values()) {
            valueMap.put(type.getValue(), type);
        }
    }
}

