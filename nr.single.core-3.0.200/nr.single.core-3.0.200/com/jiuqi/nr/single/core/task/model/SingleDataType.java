/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.model;

public enum SingleDataType {
    BOOLEAN(1, "\u5e03\u5c14"),
    DATE(2, "\u65e5\u671f\u65f6\u95f4"),
    FLOAT(3, "\u6d6e\u70b9"),
    INTEGER(5, "\u6574\u578b"),
    STRING(6, "\u5b57\u7b26"),
    CLOB(12, "\u957f\u6587\u672c"),
    FILE(5006, "\u6587\u4ef6");

    private final int val;
    private final String title;

    private SingleDataType(int val, String title) {
        this.val = val;
        this.title = title;
    }

    public int value() {
        return this.val;
    }

    public String title() {
        return this.title;
    }

    public static SingleDataType parseVal(int val) {
        for (SingleDataType type : SingleDataType.values()) {
            if (type.value() != val) continue;
            return type;
        }
        return null;
    }
}

