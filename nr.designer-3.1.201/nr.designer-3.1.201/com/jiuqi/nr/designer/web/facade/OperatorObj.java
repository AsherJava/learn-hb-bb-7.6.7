/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatorObj {
    @JsonProperty
    private String Sign;
    @JsonProperty
    private int Priority;
    @JsonProperty
    private boolean PrevOperand;
    @JsonProperty
    private boolean SuccOperand;
    @JsonProperty
    private int ResultType;

    @JsonIgnore
    public String getSign() {
        return this.Sign;
    }

    @JsonIgnore
    public void setSign(String sign) {
        this.Sign = sign;
    }

    @JsonIgnore
    public int getPriority() {
        return this.Priority;
    }

    @JsonIgnore
    public void setPriority(int priority) {
        this.Priority = priority;
    }

    @JsonIgnore
    public boolean isPrevOperand() {
        return this.PrevOperand;
    }

    @JsonIgnore
    public void setPrevOperand(boolean prevOperand) {
        this.PrevOperand = prevOperand;
    }

    @JsonIgnore
    public boolean isSuccOperand() {
        return this.SuccOperand;
    }

    @JsonIgnore
    public void setSuccOperand(boolean succOperand) {
        this.SuccOperand = succOperand;
    }

    @JsonIgnore
    public int getResultType() {
        return this.ResultType;
    }

    @JsonIgnore
    public void setResultType(int resultType) {
        this.ResultType = resultType;
    }
}

