/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum ParameterHierarchyType {
    NORMAL(0, "\u666e\u901a\u5c42\u6b21"),
    PARENT_SON(1, "\u7236\u5b50\u5c42\u6b21"),
    STRUCTURECODE(2, "\u7ed3\u6784\u7f16\u7801"),
    NONE(3, "\u65e0");

    private int value;
    private String title;
    private static Map<Integer, ParameterHierarchyType> finder;

    private ParameterHierarchyType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterHierarchyType valueOf(int value) {
        return ParameterHierarchyType.valueOf(new Integer(value));
    }

    public static ParameterHierarchyType valueOf(Integer value) {
        return finder.get(value);
    }

    public static ParameterHierarchyType parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        for (ParameterHierarchyType item : ParameterHierarchyType.values()) {
            if (!item.title().equalsIgnoreCase(s)) continue;
            return item;
        }
        return null;
    }

    static {
        finder = new HashMap<Integer, ParameterHierarchyType>();
        for (ParameterHierarchyType item : ParameterHierarchyType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

