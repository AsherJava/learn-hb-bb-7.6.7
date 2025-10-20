/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.model.hierarchy;

import java.util.HashMap;
import java.util.Map;

public enum DSHierarchyType {
    COLUMN_HIERARCHY(0, "\u5217\u95f4\u5c42\u6b21"),
    PARENT_HIERARCHY(1, "\u7236\u5b50\u5c42\u6b21"),
    CODE_HIERARCHY(2, "\u7f16\u7801\u5c42\u6b21");

    private int value;
    private String title;
    private static Map<Integer, DSHierarchyType> finder;

    private DSHierarchyType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DSHierarchyType valueOf(int value) {
        return DSHierarchyType.valueOf(new Integer(value));
    }

    public static DSHierarchyType valueOf(Integer value) {
        return finder.get(value);
    }

    public static DSHierarchyType parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        for (DSHierarchyType item : DSHierarchyType.values()) {
            if (!item.title().equalsIgnoreCase(s)) continue;
            return item;
        }
        return null;
    }

    static {
        finder = new HashMap<Integer, DSHierarchyType>();
        for (DSHierarchyType item : DSHierarchyType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

