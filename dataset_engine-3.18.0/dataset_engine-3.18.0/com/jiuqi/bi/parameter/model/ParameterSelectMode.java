/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum ParameterSelectMode {
    SINGLE(0, "\u5355\u9009"),
    MUTIPLE(1, "\u591a\u9009");

    private int value;
    private String title;
    private static Map<Integer, ParameterSelectMode> finder;

    private ParameterSelectMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterSelectMode valueOf(int value) {
        return finder.get(value);
    }

    public static ParameterSelectMode valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, ParameterSelectMode>();
        for (ParameterSelectMode item : ParameterSelectMode.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

