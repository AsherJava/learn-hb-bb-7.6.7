/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum ParameterWidgetType {
    DEFAULT(0, "\u9ed8\u8ba4"),
    DROPDOWN(1, "\u4e0b\u62c9"),
    POPUP(2, "\u5f39\u51fa"),
    FUZZYSEARCH(3, "\u6a21\u7cca\u67e5\u8be2"),
    DATEPICKER(4, "\u65e5\u5386\u63a7\u4ef6"),
    SMARTSELECTOR(5, "\u7075\u6d3b\u9009\u62e9\u5668"),
    UNITSELECTOR(6, "\u5355\u4f4d\u9009\u62e9\u5668");

    private int value;
    private String title;
    private static Map<Integer, ParameterWidgetType> finder;

    private ParameterWidgetType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterWidgetType valueOf(int value) {
        return finder.get(value);
    }

    public static ParameterWidgetType valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, ParameterWidgetType>();
        for (ParameterWidgetType item : ParameterWidgetType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

