/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

public enum DataBusinessType {
    NONE(0, "\u65e0"),
    GENERAL_DIM(1, "\u666e\u901a\u7ef4\u5ea6"),
    TIME_DIM(2, "\u65f6\u95f4\u7ef4\u5ea6"),
    UNIT_DIM(3, "\u5355\u4f4d\u7ef4\u5ea6");

    private int value;
    private String title;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private DataBusinessType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DataBusinessType valueOf(int value) {
        if (value == DataBusinessType.NONE.value) {
            return NONE;
        }
        if (value == DataBusinessType.GENERAL_DIM.value) {
            return GENERAL_DIM;
        }
        if (value == DataBusinessType.TIME_DIM.value) {
            return TIME_DIM;
        }
        if (value == DataBusinessType.UNIT_DIM.value) {
            return UNIT_DIM;
        }
        return null;
    }

    public boolean isTimeDim() {
        return this.value == DataBusinessType.TIME_DIM.value;
    }
}

