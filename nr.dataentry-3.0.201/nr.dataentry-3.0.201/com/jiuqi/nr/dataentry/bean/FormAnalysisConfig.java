/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public class FormAnalysisConfig {
    private boolean autoAnalysis;
    private String functionName;
    private String functionCondition;

    public boolean isAutoAnalysis() {
        return this.autoAnalysis;
    }

    public void setAutoAnalysis(boolean autoAnalysis) {
        this.autoAnalysis = autoAnalysis;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCondition() {
        return this.functionCondition;
    }

    public void setFunctionCondition(String functionCondition) {
        this.functionCondition = functionCondition;
    }
}

