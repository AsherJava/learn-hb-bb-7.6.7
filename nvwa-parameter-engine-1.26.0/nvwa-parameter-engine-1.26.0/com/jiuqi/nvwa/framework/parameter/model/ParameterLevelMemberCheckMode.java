/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

public enum ParameterLevelMemberCheckMode {
    SELF(0, "\u52fe\u9009\u81ea\u8eab\u8282\u70b9"),
    DIRECT_LEVEL(1, "\u52fe\u9009\u81ea\u8eab\u548c\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9"),
    ALL_SUB_LEVEL(2, "\u52fe\u9009\u81ea\u8eab\u548c\u6240\u6709\u4e0b\u7ea7\u8282\u70b9"),
    WIDGET_INNER_SELECT(3, "\u63a7\u4ef6\u5185\u5207\u6362\u52fe\u9009\u6a21\u5f0f");

    private int value;
    private String title;

    private ParameterLevelMemberCheckMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterLevelMemberCheckMode valueOf(int value) {
        if (value == 0) {
            return SELF;
        }
        if (value == 1) {
            return DIRECT_LEVEL;
        }
        if (value == 2) {
            return ALL_SUB_LEVEL;
        }
        if (value == 3) {
            return WIDGET_INNER_SELECT;
        }
        return null;
    }
}

