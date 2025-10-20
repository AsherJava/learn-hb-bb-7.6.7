/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum CellStyleModel {
    EDIT,
    READONLY,
    UNSELECT;


    public int getCode() {
        return this.ordinal();
    }

    public static CellStyleModel forInt(int code) {
        if (code < 0 || code >= CellStyleModel.values().length) {
            throw new IllegalArgumentException("Invalid CellStyleModel code: " + code);
        }
        return CellStyleModel.values()[code];
    }
}

