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

public enum ReportCacheCycleType {
    SETTING_BEFORE_CYCLE(0, "\u586b\u62a5\u5468\u671f\u4e4b\u524d"),
    SETTING_AFTER_CYCLE(1, "\u586b\u62a5\u5468\u671f\u4e4b\u540e");

    private final int value;
    private final String title;
    private static final Map<Integer, ReportCacheCycleType> valueMap;

    private ReportCacheCycleType(int code, String title) {
        this.value = code;
        this.title = title;
    }

    public static ReportCacheCycleType valueOf(int value) {
        return valueMap.get(value);
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    @JsonCreator
    public ReportCacheCycleType fromValue(@JsonProperty(value="value") int value) {
        for (ReportCacheCycleType option : ReportCacheCycleType.values()) {
            if (option.getValue() != value) continue;
            return option;
        }
        return null;
    }

    static {
        valueMap = new HashMap<Integer, ReportCacheCycleType>();
        for (ReportCacheCycleType type : ReportCacheCycleType.values()) {
            valueMap.put(type.getValue(), type);
        }
    }
}

