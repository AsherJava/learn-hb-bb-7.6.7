/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.constant;

public enum FillPatternType {
    NO_FILL,
    SOLID_FOREGROUND,
    FINE_DOTS,
    ALT_BARS,
    SPARSE_DOTS,
    THICK_HORZ_BANDS,
    THICK_VERT_BANDS,
    THICK_BACKWARD_DIAG,
    THICK_FORWARD_DIAG,
    BIG_SPOTS,
    BRICKS,
    THIN_HORZ_BANDS,
    THIN_VERT_BANDS,
    THIN_BACKWARD_DIAG,
    THIN_FORWARD_DIAG,
    SQUARES,
    DIAMONDS,
    LESS_DOTS,
    LEAST_DOTS;


    public int getCode() {
        return this.ordinal();
    }

    public static FillPatternType forInt(int code) {
        if (code < 0 || code >= FillPatternType.values().length) {
            throw new IllegalArgumentException("Invalid FillPatternType code: " + code);
        }
        return FillPatternType.values()[code];
    }
}

