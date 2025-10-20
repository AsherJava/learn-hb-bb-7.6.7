/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

import java.util.HashMap;
import java.util.Map;

public enum ParameterWidgetType {
    DEFAULT(0, "\u9ed8\u8ba4"),
    DROPDOWN(1, "\u4e0b\u62c9"),
    POPUP(2, "\u5f39\u51fa"),
    FUZZYSEARCH(3, "\u6a21\u7cca\u67e5\u8be2"),
    DATEPICKER(4, "\u65e5\u5386\u63a7\u4ef6"),
    DATEPICKER_RANGE(41, "\u65e5\u5386\u8303\u56f4\u63a7\u4ef6(\u5355\u4e2a\u9009\u62e9\u6846)"),
    SMARTSELECTOR(5, "\u7075\u6d3b\u9009\u62e9\u5668"),
    UNITSELECTOR(6, "\u5355\u4f4d\u9009\u62e9\u5668"),
    BUTTON(7, "\u6309\u94ae");

    private final int value;
    private final String title;
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

    static {
        finder = new HashMap<Integer, ParameterWidgetType>();
        for (ParameterWidgetType item : ParameterWidgetType.values()) {
            finder.put(item.value(), item);
        }
    }
}

