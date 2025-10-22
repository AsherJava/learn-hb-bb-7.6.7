/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.state.common;

enum TypeConst {
    ENDFILL(0, "\u7ec8\u6b62\u586b\u62a5\u64cd\u4f5c");

    private int value;
    private String title;

    private TypeConst(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

