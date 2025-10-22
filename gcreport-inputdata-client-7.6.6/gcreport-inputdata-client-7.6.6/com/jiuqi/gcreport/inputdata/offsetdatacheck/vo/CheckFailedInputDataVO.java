/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.offsetdatacheck.vo;

public class CheckFailedInputDataVO {
    private String id;
    private String taskTitle;
    private String formTitle;
    private String periodStr;
    private String currencyTitle;
    private String unitTitle;
    private Double amt;
    private Double offsetAmt;
    private Double diffAmt;
    private String oppUnitTitle;
    private String offsetGroupId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCurrencyTitle() {
        return this.currencyTitle;
    }

    public void setCurrencyTitle(String currencyTitle) {
        this.currencyTitle = currencyTitle;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getOffsetAmt() {
        return this.offsetAmt;
    }

    public void setOffsetAmt(Double offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public Double getDiffAmt() {
        return this.diffAmt;
    }

    public void setDiffAmt(Double diffAmt) {
        this.diffAmt = diffAmt;
    }

    public String getOppUnitTitle() {
        return this.oppUnitTitle;
    }

    public void setOppUnitTitle(String oppUnitTitle) {
        this.oppUnitTitle = oppUnitTitle;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }
}

