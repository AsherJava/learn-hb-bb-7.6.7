/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum ParameterDimType {
    NONE(0, "\u65e0"),
    GENERAL_DIM(1, "\u666e\u901a\u7ef4\u5ea6"),
    TIME_DIM(2, "\u65f6\u95f4\u7ef4\u5ea6"),
    UNIT_DIM(3, "\u5355\u4f4d\u7ef4\u5ea6");

    private int value;
    private String title;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    private static Map<Integer, ParameterDimType> finder;

    private ParameterDimType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterDimType valueOf(int value) {
        return ParameterDimType.valueOf(new Integer(value));
    }

    public static ParameterDimType valueOf(Integer value) {
        return finder.get(value);
    }

    public boolean isTimeDim() {
        return this.value == ParameterDimType.TIME_DIM.value;
    }

    static {
        finder = new HashMap<Integer, ParameterDimType>();
        for (ParameterDimType item : ParameterDimType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

