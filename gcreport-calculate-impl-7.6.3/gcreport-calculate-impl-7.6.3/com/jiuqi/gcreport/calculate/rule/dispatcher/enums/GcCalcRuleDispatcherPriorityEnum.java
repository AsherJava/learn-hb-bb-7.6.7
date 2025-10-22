/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.calculate.rule.dispatcher.enums;

public enum GcCalcRuleDispatcherPriorityEnum {
    MAX_PRIORITY(10, "\u9ad8\u4f18\u5148\u7ea7"),
    NORM_PRIORITY(5, "\u666e\u901a\u4f18\u5148\u7ea7"),
    MIN_PRIORITY(1, "\u4f4e\u4f18\u5148\u7ea7");

    private int order;
    private String title;

    private GcCalcRuleDispatcherPriorityEnum(int order, String title) {
        this.order = order;
        this.title = title;
    }

    public int getOrder() {
        return this.order;
    }

    public String getTitle() {
        return this.title;
    }
}

