/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum HorizontalAlignment {
    GENERAL,
    LEFT,
    CENTER,
    RIGHT,
    FILL,
    JUSTIFY,
    CENTER_SELECTION,
    DISTRIBUTED;


    public int getCode() {
        return this.ordinal();
    }

    public static HorizontalAlignment forInt(int code) {
        if (code < 0 || code >= HorizontalAlignment.values().length) {
            throw new IllegalArgumentException("Invalid CellBorderStyle code: " + code);
        }
        return HorizontalAlignment.values()[code];
    }
}

