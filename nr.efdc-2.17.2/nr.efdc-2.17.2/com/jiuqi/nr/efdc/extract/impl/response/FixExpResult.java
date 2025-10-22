/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.response;

import java.util.ArrayList;
import java.util.List;

public class FixExpResult {
    private String flag;
    private String dataType;
    private List<String> expression = new ArrayList<String>();
    private List<String> values = new ArrayList<String>();
    private boolean valid = true;

    public List<String> getExpression() {
        return this.expression;
    }

    public void setExpression(List<String> expression) {
        this.expression = expression;
    }

    public String getExpression(int index) {
        return this.expression.get(index);
    }

    public void addExpression(String expression) {
        this.expression.add(expression);
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return this.valid;
    }
}

