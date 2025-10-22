/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum FieldType {
    MASTER(1, "\u4e3b\u7ef4\u5ea6"),
    PERIOD(2, "\u65f6\u671f"),
    SCENE(3, "\u60c5\u666f"),
    TABLEDIMENSION(4, "\u8868\u5185\u7ef4\u5ea6"),
    ZB(5, "\u6307\u6807"),
    FIELD(6, "\u5b57\u6bb5"),
    EXPRESSION(10, "\u8ba1\u7b97\u5b57\u6bb5"),
    FLOAT_ID(11, "\u6d6e\u52a8\u884c\u4e3b\u952e"),
    ADJUST(12, "\u8c03\u6574\u671f");

    private int value;
    private String title;

    private FieldType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static FieldType valueOf(int value) {
        for (FieldType type : FieldType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

