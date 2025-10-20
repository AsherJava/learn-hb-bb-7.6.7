/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.bde.bizmodel.client.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomCondition {
    private String paramsCode;
    private String paramsName;
    private String ruleCode;
    private Boolean required;
    @JsonIgnore
    private String value;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParamsCode() {
        return this.paramsCode;
    }

    public void setParamsCode(String paramsCode) {
        this.paramsCode = paramsCode;
    }

    public String getParamsName() {
        return this.paramsName;
    }

    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}

