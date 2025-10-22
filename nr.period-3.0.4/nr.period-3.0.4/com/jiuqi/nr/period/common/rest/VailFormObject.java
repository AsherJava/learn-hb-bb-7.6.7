/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

public class VailFormObject {
    private String validType;
    private String tableCode;
    private String periodKey;
    private String operator;

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPeriodKey() {
        return this.periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getValidType() {
        return this.validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}

