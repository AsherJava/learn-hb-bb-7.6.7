/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.model;

public enum DefaultValueMode {
    NONE(0, "\u65e0"),
    FIRST(1, "\u7b2c\u4e00\u4e2a\u503c"),
    FIRST_CHILD(101, "\u7b2c\u4e00\u4e2a\u503c\u53ca\u76f4\u63a5\u4e0b\u7ea7"),
    CURRENT(2, "\u5f53\u524d\u671f"),
    PREVIOUS(3, "\u4e0a\u4e00\u671f"),
    APPOINT(9, "\u6307\u5b9a\u53d6\u503c");

    private int value;
    private String title;

    private DefaultValueMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DefaultValueMode valueOf(int value) {
        for (DefaultValueMode type : DefaultValueMode.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

