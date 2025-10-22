/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.request;

public class FixExpression {
    private String flag;
    private String name;
    private int precision = 2;
    private String expression;

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

