/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.variable;

import com.jiuqi.nr.analysisreport.variable.VariableType;
import java.io.Serializable;

public class Variable
implements Serializable {
    private static final long serialVersionUID = 5833177941713993474L;
    protected VariableType variableType;
    private String name;
    private String expression;
    private String caption;

    public VariableType getVariableType() {
        return this.variableType;
    }

    protected void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Name:").append(this.getName()).append(",");
        sb.append("VariableType:").append((Object)this.variableType).append(",");
        sb.append("Expression:").append(this.getExpression()).append(",");
        sb.append("Caption:").append(this.getCaption()).append("}");
        return sb.toString();
    }
}

