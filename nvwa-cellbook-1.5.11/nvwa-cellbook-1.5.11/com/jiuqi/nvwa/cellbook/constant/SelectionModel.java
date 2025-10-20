/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum SelectionModel {
    SINGLE,
    ROW,
    COLUMN,
    READONLY;


    public int getCode() {
        return this.ordinal();
    }

    public static SelectionModel forInt(int code) {
        if (code < 0 || code >= SelectionModel.values().length) {
            throw new IllegalArgumentException("Invalid SelectionModel code: " + code);
        }
        return SelectionModel.values()[code];
    }
}

