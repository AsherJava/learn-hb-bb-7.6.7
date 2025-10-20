/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum DocModel {
    EDIT,
    READONLY,
    DESIGN;


    public int getCode() {
        return this.ordinal();
    }

    public static DocModel forInt(int code) {
        if (code < 0 || code >= DocModel.values().length) {
            throw new IllegalArgumentException("Invalid DocModel code: " + code);
        }
        return DocModel.values()[code];
    }
}

