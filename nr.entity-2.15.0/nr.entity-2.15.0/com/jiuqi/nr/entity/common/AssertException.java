/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.common;

public enum AssertException {
    ILLEGAL_ARGUMENT("\u975e\u6cd5\u7684\u53c2\u6570"),
    NOT_SAME_ID("\u4e3b\u952e\u4e0d\u540c"),
    NULL_PARAM_PROPERTIES("\u53c2\u6570\u7684\u5c5e\u6027\u503c\u4e3a\u7a7a"),
    NULL_ARGUMENT("\u53c2\u6570\u503c\u4e3a\u7a7a");

    private final String message;

    private AssertException(String message) {
        this.message = message;
    }

    public String getMessage(String arg) {
        return new StringBuffer(this.message).append(":").append(arg).toString();
    }
}

