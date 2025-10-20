/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.formula;

public class FuncTraceNode {
    private String code;
    private String regEx;
    private String showName;

    public FuncTraceNode() {
    }

    public FuncTraceNode(String code, String regEx, String showName) {
        this.code = code;
        this.regEx = regEx;
        this.showName = showName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegEx() {
        return this.regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}

