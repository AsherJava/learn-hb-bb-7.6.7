/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

public class BillExtractField {
    private String columnName;
    private String columnTitle;
    private String columnType;
    private String columnText;
    private int publishState;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnTitle() {
        return this.columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnText() {
        return this.columnText;
    }

    public void setColumnText(String columnText) {
        this.columnText = columnText;
    }

    public int getPublishState() {
        return this.publishState;
    }

    public void setPublishState(int publishState) {
        this.publishState = publishState;
    }
}

