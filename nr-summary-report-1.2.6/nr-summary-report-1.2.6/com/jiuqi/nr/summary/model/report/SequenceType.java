/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

public enum SequenceType {
    NONE(0, "\u65e0"),
    TYPE1(1, "\u7532\u3001\u5df2\u3001\u4e19..."),
    TYPE2(2, "1\u30012\u30013..."),
    TYPE3(3, "A\u3001B\u3001C...");

    private int value;
    private String title;

    private SequenceType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SequenceType valueOf(int value) {
        for (SequenceType type : SequenceType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

