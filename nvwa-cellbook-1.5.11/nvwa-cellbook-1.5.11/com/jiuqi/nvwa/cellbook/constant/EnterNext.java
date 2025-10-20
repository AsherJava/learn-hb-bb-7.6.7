/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum EnterNext {
    AUTO,
    BOTTOM,
    RIGHT,
    TOP,
    LEFT;


    public int getCode() {
        return this.ordinal();
    }

    public static EnterNext forInt(int code) {
        if (code < 0 || code >= EnterNext.values().length) {
            throw new IllegalArgumentException("Invalid DocModel code: " + code);
        }
        return EnterNext.values()[code];
    }
}

