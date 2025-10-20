/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum VerticalAlignment {
    AUTO,
    TOP,
    CENTER,
    BOTTOM,
    JUSTIFY,
    DISTRIBUTED;


    public int getCode() {
        return this.ordinal();
    }

    public static VerticalAlignment forInt(int code) {
        if (code < 0 || code >= VerticalAlignment.values().length) {
            throw new IllegalArgumentException("Invalid VerticalAlignment code: " + code);
        }
        return VerticalAlignment.values()[code];
    }
}

