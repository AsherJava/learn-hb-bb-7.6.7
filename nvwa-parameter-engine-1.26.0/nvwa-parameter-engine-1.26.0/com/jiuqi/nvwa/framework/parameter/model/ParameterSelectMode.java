/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

public enum ParameterSelectMode {
    SINGLE(0, "\u5355\u9009"),
    MUTIPLE(1, "\u591a\u9009"),
    RANGE(2, "\u8303\u56f4");

    private int value;
    private String title;

    private ParameterSelectMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterSelectMode valueOf(int value) {
        if (value == 0) {
            return SINGLE;
        }
        if (value == 1) {
            return MUTIPLE;
        }
        if (value == 2) {
            return RANGE;
        }
        return null;
    }
}

