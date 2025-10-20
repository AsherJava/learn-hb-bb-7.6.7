/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model.datasource;

import java.util.HashMap;
import java.util.Map;

public enum DataSourceFilterMode {
    APPOINT(0, "\u6307\u5b9a\u6210\u5458"),
    ALL(1, "\u5168\u90e8\u6210\u5458"),
    EXPRESSION(2, "\u8868\u8fbe\u5f0f");

    private int value;
    private String title;
    private static Map<Integer, DataSourceFilterMode> finder;

    private DataSourceFilterMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DataSourceFilterMode valueOf(int value) {
        return finder.get(value);
    }

    public static DataSourceFilterMode valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, DataSourceFilterMode>();
        for (DataSourceFilterMode item : DataSourceFilterMode.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

