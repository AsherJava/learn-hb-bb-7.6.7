/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model.enums;

public enum CellType {
    NUMBER(0, "\u6570\u503c\u5355\u5143\u683c"),
    STRING(1, "\u5b57\u7b26\u5355\u5143\u683c"),
    DATE(2, "\u65e5\u671f\u5355\u5143\u683c"),
    ENUM(3, "\u679a\u4e3e\u5355\u5143\u683c"),
    BOOLEAN(4, "\u5e03\u5c14\u5355\u5143\u683c"),
    EXPRESSION(5, "\u516c\u5f0f\u5355\u5143\u683c"),
    PICTURE(6, "\u56fe\u7247\u5355\u5143\u683c"),
    FILE(7, "\u9644\u4ef6\u5355\u5143\u683c");

    private int value;
    private String title;

    private CellType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CellType valueOf(int value) {
        for (CellType type : CellType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

