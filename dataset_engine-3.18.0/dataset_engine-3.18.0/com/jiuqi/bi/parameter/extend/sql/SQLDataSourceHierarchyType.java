/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.sql;

import java.util.HashMap;
import java.util.Map;

public enum SQLDataSourceHierarchyType {
    NONE(0, "\u65e0"),
    PARENTMODE(1, "\u7236\u5b50\u5c42\u7ea7"),
    STRUCTURECODE(2, "\u7ed3\u6784\u7f16\u7801");

    private int value;
    private String title;
    private static Map<Integer, SQLDataSourceHierarchyType> finder;

    private SQLDataSourceHierarchyType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SQLDataSourceHierarchyType valueOf(int value) {
        return finder.get(value);
    }

    public static SQLDataSourceHierarchyType valueOf(Integer value) {
        return finder.get(value);
    }

    static {
        finder = new HashMap<Integer, SQLDataSourceHierarchyType>();
        for (SQLDataSourceHierarchyType item : SQLDataSourceHierarchyType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

