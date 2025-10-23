/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.cell;

@Deprecated
public enum ZBDataType {
    UNKNOWN(0, "\u672a\u77e5"),
    NUMERIC(1, "\u6570\u503c\u578b"),
    STRING(2, "\u5b57\u7b26\u578b"),
    INTEGER(3, "\u6574\u6570\u578b"),
    BOOLEAN(4, "\u5e03\u5c14\u578b"),
    DATE(5, "\u65e5\u671f\u578b"),
    DATETIME(6, "\u65e5\u671f\u65f6\u95f4\u578b"),
    GUID(7, "GUID\u578b"),
    REMARK(8, "\u5907\u6ce8\u578b"),
    BINARY(9, "\u4e8c\u8fdb\u5236\u578b");

    private int value;
    private String title;

    private ZBDataType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ZBDataType valueOf(int value) {
        for (ZBDataType type : ZBDataType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

