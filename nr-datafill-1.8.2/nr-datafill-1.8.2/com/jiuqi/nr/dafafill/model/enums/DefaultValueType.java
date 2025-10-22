/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum DefaultValueType {
    CURRENT(1, "\u5f53\u524d\u503c"),
    LAST(2, "\u4e0a\u4e00\u671f"),
    NONE(3, "\u65e0"),
    FIRST(4, "\u7b2c\u4e00\u503c"),
    SPECIFIC(5, "\u6307\u5b9a\u503c"),
    UCURRENT(11, "\u672c\u7ea7\u8282\u70b9"),
    UDIRECTSUB(12, "\u76f4\u63a5\u4e0b\u7ea7"),
    UALLSUB(13, "\u6240\u6709\u4e0b\u7ea7"),
    UCURRENTDIRECTSUB(14, "\u672c\u7ea7+\u76f4\u63a5\u4e0b\u7ea7"),
    UCURRENTALLSUB(15, "\u672c\u7ea7+\u6240\u6709\u4e0b\u7ea7"),
    USELECTION(16, "\u6839\u636e\u6240\u9009"),
    UFILTER(17, "\u6839\u636e\u6761\u4ef6\u8fc7\u6ee4");

    private int value;
    private String title;

    private DefaultValueType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DefaultValueType valueOf(int value) {
        for (DefaultValueType type : DefaultValueType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

