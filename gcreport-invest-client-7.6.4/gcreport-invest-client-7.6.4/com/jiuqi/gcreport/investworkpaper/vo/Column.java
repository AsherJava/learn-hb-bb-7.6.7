/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investworkpaper.vo;

public class Column {
    private String columnKey;
    private String columnTitle;
    private String billCode;

    public Column(String columnKey, String columnTitle) {
        this.columnKey = columnKey;
        this.columnTitle = columnTitle;
    }

    public String getColumnKey() {
        return this.columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnTitle() {
        return this.columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
}

