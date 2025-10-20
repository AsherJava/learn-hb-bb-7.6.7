/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain;

public class SublistExportDTO {
    private String billCode;
    private String defineCode;
    private String params;
    private String curView;
    private String curTimezone;
    private Object modelContext;

    public Object getModelContext() {
        return this.modelContext;
    }

    public void setModelContext(Object modelContext) {
        this.modelContext = modelContext;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCurView() {
        return this.curView;
    }

    public void setCurView(String curView) {
        this.curView = curView;
    }

    public String getCurTimezone() {
        return this.curTimezone;
    }

    public void setCurTimezone(String curTimezone) {
        this.curTimezone = curTimezone;
    }
}

