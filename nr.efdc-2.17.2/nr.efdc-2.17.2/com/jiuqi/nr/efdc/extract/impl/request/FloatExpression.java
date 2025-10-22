/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.request;

import com.jiuqi.nr.efdc.extract.impl.request.FixExpression;
import java.util.ArrayList;
import java.util.List;

public class FloatExpression {
    private String flag;
    private String name;
    private int precision = 2;
    private String expression;
    private List<FixExpression> colExpressions = new ArrayList<FixExpression>();

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

    public List<FixExpression> getColExpressions() {
        return this.colExpressions;
    }

    public void setColExpressions(List<FixExpression> colExpressions) {
        this.colExpressions = colExpressions;
    }
}

