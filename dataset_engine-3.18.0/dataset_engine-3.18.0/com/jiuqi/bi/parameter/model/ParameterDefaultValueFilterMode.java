/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum ParameterDefaultValueFilterMode {
    APPOINT(0, "\u6307\u5b9a\u6210\u5458"),
    FIRST(1, "\u7b2c\u4e00\u4e2a"),
    FIRST_CHILD(11, "\u7b2c\u4e00\u4e2a\u53ca\u76f4\u63a5\u4e0b\u7ea7"),
    EXPRESSION(2, "\u8868\u8fbe\u5f0f"),
    NONE(3, "\u65e0");

    private int value;
    private String title;
    private static Map<Integer, ParameterDefaultValueFilterMode> finder;

    private ParameterDefaultValueFilterMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public String title() {
        return this.title;
    }

    public int value() {
        return this.value;
    }

    public static ParameterDefaultValueFilterMode valueOf(int value) {
        return finder.get(value);
    }

    public static ParameterDefaultValueFilterMode valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, ParameterDefaultValueFilterMode>();
        for (ParameterDefaultValueFilterMode item : ParameterDefaultValueFilterMode.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

