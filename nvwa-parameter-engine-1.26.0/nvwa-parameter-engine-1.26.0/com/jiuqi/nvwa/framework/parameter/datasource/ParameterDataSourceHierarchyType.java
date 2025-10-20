/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

public enum ParameterDataSourceHierarchyType {
    NONE(3, "\u65e0"),
    PARENTMODE(1, "\u7236\u5b50\u5c42\u7ea7"),
    STRUCTURECODE(2, "\u7ed3\u6784\u7f16\u7801"),
    NORMAL(0, "\u666e\u901a\u5c42\u6b21");

    private int value;
    private String title;

    private ParameterDataSourceHierarchyType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterDataSourceHierarchyType valueOf(int value) {
        if (value == 0) {
            return NORMAL;
        }
        if (value == 1) {
            return PARENTMODE;
        }
        if (value == 2) {
            return STRUCTURECODE;
        }
        if (value == 3) {
            return NONE;
        }
        return null;
    }
}

