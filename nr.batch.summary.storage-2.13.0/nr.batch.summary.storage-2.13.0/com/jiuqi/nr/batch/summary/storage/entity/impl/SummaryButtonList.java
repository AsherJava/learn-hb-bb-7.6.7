/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

public class SummaryButtonList {
    private boolean summary;
    private boolean batchSummary;
    private boolean calc;
    private boolean calcAll;
    private boolean check;
    private boolean checkAll;
    private boolean summaryDetails;

    public boolean isSummary() {
        return this.summary;
    }

    public void setSummary(boolean summary) {
        this.summary = summary;
    }

    public boolean isBatchSummary() {
        return this.batchSummary;
    }

    public void setBatchSummary(boolean batchSummary) {
        this.batchSummary = batchSummary;
    }

    public boolean isCalc() {
        return this.calc;
    }

    public void setCalc(boolean calc) {
        this.calc = calc;
    }

    public boolean isCalcAll() {
        return this.calcAll;
    }

    public void setCalcAll(boolean calcAll) {
        this.calcAll = calcAll;
    }

    public boolean isCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckAll() {
        return this.checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    public boolean isSummaryDetails() {
        return this.summaryDetails;
    }

    public void setSummaryDetails(boolean summaryDetails) {
        this.summaryDetails = summaryDetails;
    }
}

