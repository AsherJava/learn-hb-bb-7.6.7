/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2;

public enum FontStyles2 {
    FONT_BOLD(2),
    FONT_ITALIC(4),
    FONT_U_SINGLE(8),
    FONT_STRIKE_OUT(16);

    int value;

    private FontStyles2(int value) {
        this.value = value;
    }

    public int getValue(FontStyles2 style) {
        return this.value;
    }
}

