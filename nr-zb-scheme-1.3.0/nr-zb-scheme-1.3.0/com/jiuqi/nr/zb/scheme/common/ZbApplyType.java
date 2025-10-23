/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nvwa.definition.common.ApplyType
 */
package com.jiuqi.nr.zb.scheme.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nvwa.definition.common.ApplyType;
import java.util.HashMap;
import java.util.Map;

public enum ZbApplyType {
    NONE(ApplyType.NONE, "\u65e0"),
    PERIODIC(ApplyType.PERIODIC, "\u65f6\u671f\u6570"),
    TIME_POINT(ApplyType.TIME_POINT, "\u65f6\u70b9\u6570"),
    OPENING_BALANCE(ApplyType.OPENING_BALANCE, "\u671f\u521d\u6570"),
    CLOSING_BALANCE(ApplyType.CLOSING_BALANCE, "\u671f\u672b\u6570"),
    SUM(ApplyType.SUM, "\u7d2f\u8ba1\u6570");

    private final String title;
    private final int value;
    public static final Map<Integer, ZbApplyType> mappings;
    public static final Map<String, ZbApplyType> title_mappings;

    private ZbApplyType(ApplyType applyType, String title) {
        this.title = title;
        this.value = applyType.getValue();
    }

    public String getTitle() {
        return this.title;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    @JsonCreator
    public static ZbApplyType forValue(int value) {
        return mappings.get(value);
    }

    public static ZbApplyType forTitle(String title) {
        return title_mappings.get(title);
    }

    static {
        mappings = new HashMap<Integer, ZbApplyType>();
        title_mappings = new HashMap<String, ZbApplyType>();
        for (ZbApplyType type : ZbApplyType.values()) {
            mappings.put(type.getValue(), type);
            title_mappings.put(type.getTitle(), type);
        }
    }
}

