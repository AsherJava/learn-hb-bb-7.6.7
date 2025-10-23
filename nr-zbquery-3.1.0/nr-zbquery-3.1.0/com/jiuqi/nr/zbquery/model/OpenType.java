/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

public enum OpenType {
    FUNCTAB("FUNCTAB", "\u7cfb\u7edf\u5185\u9875\u7b7e"),
    OPENBLANK("OPENBLANK", "\u6d4f\u89c8\u5668\u9875\u7b7e"),
    MODELWINDOW("MODELWINDOW", "\u6a21\u6001\u7a97\u53e3"),
    ATTACHMENT("ATTACHMENT", "\u533a\u5206\u8d85\u94fe\u63a5\u4e0e\u9644\u4ef6");

    private String value;
    private String title;

    private OpenType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }
}

