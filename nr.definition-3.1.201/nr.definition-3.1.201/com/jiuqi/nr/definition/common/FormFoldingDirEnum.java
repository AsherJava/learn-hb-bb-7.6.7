/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.HashMap;
import java.util.Map;

public enum FormFoldingDirEnum {
    ROW_DIRECTION(0, "\u884c\u6298\u53e0"),
    COL_DIRECTION(1, "\u5217\u6298\u53e0");

    private final int value;
    private final String title;
    private static final Map<Integer, FormFoldingDirEnum> valueMap;

    public static FormFoldingDirEnum valueOf(int value) {
        return valueMap.get(value);
    }

    private FormFoldingDirEnum(int code, String title) {
        this.value = code;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    static {
        valueMap = new HashMap<Integer, FormFoldingDirEnum>();
        for (FormFoldingDirEnum type : FormFoldingDirEnum.values()) {
            valueMap.put(type.getValue(), type);
        }
    }
}

