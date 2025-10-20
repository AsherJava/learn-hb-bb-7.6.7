/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum CellBorderStyle {
    NONE,
    THIN,
    MEDIUM,
    DASHED,
    DOTTED,
    THICK,
    DOUBLE,
    HAIR,
    MEDIUM_DASHED,
    DASH_DOT,
    MEDIUM_DASH_DOT,
    DASH_DOT_DOT,
    MEDIUM_DASH_DOT_DOT,
    SLANTED_DASH_DOT;


    public int getCode() {
        return this.ordinal();
    }

    public static CellBorderStyle forInt(int code) {
        if (code < 0 || code >= CellBorderStyle.values().length) {
            throw new IllegalArgumentException("Invalid CellBorderStyle code: " + code);
        }
        return CellBorderStyle.values()[code];
    }
}

