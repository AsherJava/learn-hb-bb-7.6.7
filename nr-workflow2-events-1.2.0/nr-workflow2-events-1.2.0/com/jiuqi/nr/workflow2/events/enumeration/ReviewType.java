/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.enumeration;

public enum ReviewType {
    FORMULA_REVIEW("\u516c\u5f0f\u5ba1\u6838"),
    COMPREHENSIVE_REVIEW("\u7efc\u5408\u5ba1\u6838");

    public final String title;

    private ReviewType(String title) {
        this.title = title;
    }
}

