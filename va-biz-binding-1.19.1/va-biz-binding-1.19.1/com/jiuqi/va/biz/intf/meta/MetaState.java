/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import com.jiuqi.va.biz.intf.value.EnumValue;

public enum MetaState implements EnumValue
{
    DEPLOYED(0, "\u53d1\u5e03"),
    APPENDED(1, "\u65b0\u589e"),
    MODIFIED(2, "\u4fee\u6539"),
    CONFLICT(3, "\u51b2\u7a81"),
    DELETED(4, "\u5220\u9664");

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

    private MetaState(int value, String title) {
        this.value = value;
        this.title = title;
    }
}

