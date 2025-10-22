/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billextract.client.dto;

import java.util.List;

public class BillExtractLisDTO {
    private String unitCode;
    private String startDate;
    private String endDate;
    private List<String> billCodeList;

    public BillExtractLisDTO() {
    }

    public BillExtractLisDTO(String unitCode, String startDate, String endDate) {
        this.unitCode = unitCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getBillCodeList() {
        return this.billCodeList;
    }

    public void setBillCodeList(List<String> billCodeList) {
        this.billCodeList = billCodeList;
    }

    public String toString() {
        return "BillExtractLisDTO [startDate=" + this.startDate + ", endDate=" + this.endDate + ", billCodeList=" + this.billCodeList + "]";
    }
}

