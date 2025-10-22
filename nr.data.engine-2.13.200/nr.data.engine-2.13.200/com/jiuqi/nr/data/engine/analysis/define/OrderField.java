/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.define;

public class OrderField {
    private String expression;
    private boolean desc = false;

    public OrderField(String expression, boolean desc) {
        this.expression = expression;
        this.desc = desc;
    }

    public String getExpression() {
        return this.expression;
    }

    public boolean isDesc() {
        return this.desc;
    }

    public String toString() {
        return "OrderField [expression=" + this.expression + ", desc=" + this.desc + "]";
    }
}

