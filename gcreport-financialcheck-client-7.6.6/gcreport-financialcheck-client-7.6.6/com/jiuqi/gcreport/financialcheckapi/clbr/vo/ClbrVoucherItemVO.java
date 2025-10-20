/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.clbr.vo;

public class ClbrVoucherItemVO {
    private String oppUnitId;
    private String gcNumber;
    private String billCode;
    private Double amt;

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String toString() {
        return "ClbrVoucherItemVO{oppUnitId='" + this.oppUnitId + '\'' + ", gcNumber='" + this.gcNumber + '\'' + ", billCode='" + this.billCode + '\'' + ", amt=" + this.amt + '}';
    }
}

