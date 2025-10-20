/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.value.EnumValue;

public enum DataState implements EnumValue
{
    NONE(0, "\u7a7a\u95f2"),
    NEW(1, "\u65b0\u589e"),
    EDIT(2, "\u7f16\u8f91"),
    BROWSE(3, "\u6d4f\u89c8");

    private int value;
    private String title;

    private DataState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}

