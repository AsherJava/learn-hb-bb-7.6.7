/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.datatype;

public enum CommonCellDataType {
    STRING("t"),
    NUMBER("n"),
    BOOLEAN("b"),
    DATE("d"),
    ERROR("e");

    private String code;

    private CommonCellDataType(String code) {
        this.code = code;
    }

    public static CommonCellDataType getOpenMoreType(String code) {
        for (CommonCellDataType c : CommonCellDataType.values()) {
            if (!c.getCode().equals(code)) continue;
            return c;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }
}

