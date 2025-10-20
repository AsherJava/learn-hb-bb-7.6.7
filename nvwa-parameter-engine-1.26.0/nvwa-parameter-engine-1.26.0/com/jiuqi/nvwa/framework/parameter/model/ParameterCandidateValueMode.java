/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

public enum ParameterCandidateValueMode {
    APPOINT(0, "\u6307\u5b9a\u6210\u5458"),
    ALL(1, "\u5168\u90e8\u6210\u5458"),
    EXPRESSION(2, "\u8868\u8fbe\u5f0f");

    private int value;
    private String title;

    private ParameterCandidateValueMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ParameterCandidateValueMode valueOf(int value) {
        if (value == 0) {
            return APPOINT;
        }
        if (value == 1) {
            return ALL;
        }
        if (value == 2) {
            return EXPRESSION;
        }
        return null;
    }
}

