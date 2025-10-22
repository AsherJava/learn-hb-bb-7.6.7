/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.response;

public class SyntaxCheckResult {
    private String flag;
    private String expType;
    private String expression;
    private String errMsg = "";

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getExpType() {
        return this.expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

