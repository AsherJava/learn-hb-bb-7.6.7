/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf;

import com.jiuqi.va.biz.intf.value.EnumValue;

public enum CheckFieldValueEnum implements EnumValue
{
    NOT_EXIST(0, "\u6570\u636e\u9879\u4e0d\u5b58\u5728\u6216\u6ca1\u6709\u6570\u636e\u9879\u6743\u9650"),
    EXPRESSION_FILTER(1, "\u4e0d\u6ee1\u8db3\u8fc7\u6ee4\u6761\u4ef6"),
    ONLY_LEAF(2, "\u9650\u5b9a\u660e\u7ec6\u9879"),
    ONLY_NOT_LEAF(3, "\u9650\u5b9a\u975e\u660e\u7ec6\u9879"),
    OTHER_ERROR(99, "\u5176\u4ed6\u9519\u8bef");

    private int value;
    private String title;

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    private CheckFieldValueEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }
}

