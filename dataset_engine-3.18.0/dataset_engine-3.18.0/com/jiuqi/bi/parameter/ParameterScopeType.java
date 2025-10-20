/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter;

import java.util.HashMap;
import java.util.Map;

public enum ParameterScopeType {
    CUBE(1, "\u7ef4\u5ea6"),
    ZB(2, "\u6307\u6807");

    private String title;
    private int value;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    private static Map<Integer, ParameterScopeType> finder;

    private ParameterScopeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterScopeType valueOf(int value) {
        return ParameterScopeType.valueOf(new Integer(value));
    }

    public static ParameterScopeType valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, ParameterScopeType>();
        for (ParameterScopeType item : ParameterScopeType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

