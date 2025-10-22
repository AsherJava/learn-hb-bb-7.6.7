/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

public enum DataType {
    BOOL(1),
    NUMERIC(2),
    INT(3),
    LONG(4),
    DOUBLE(5),
    STRING(6),
    DATE(7),
    TIME(8),
    DATETIME(9),
    FORMULA(10),
    BLANK(11),
    ENUM(13);

    int code;

    private DataType(int code) {
        this.code = code;
    }

    int getCode() {
        return this.code;
    }
}

