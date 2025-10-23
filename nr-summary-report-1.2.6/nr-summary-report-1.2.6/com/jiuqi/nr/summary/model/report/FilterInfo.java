/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

import java.io.Serializable;

public class FilterInfo
implements Serializable {
    private int position;
    private String expression;

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

