/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

public class CalibreExpressionDO {
    private Object expression;
    private Boolean sum;
    private String entityKey;

    public Object getExpression() {
        return this.expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public Boolean getSum() {
        return this.sum != null && this.sum != false;
    }

    public void setSum(Boolean sum) {
        this.sum = sum;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }
}

