/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

public class ClbrBillTableColumnVO {
    private String columnName;
    private String columnTitle;
    private String columnType;
    private String columnRefTableFeild;

    public ClbrBillTableColumnVO() {
    }

    public ClbrBillTableColumnVO(String columnName, String columnTitle, String columnType, String columnRefTableFeild) {
        this.columnName = columnName;
        this.columnTitle = columnTitle;
        this.columnType = columnType;
        this.columnRefTableFeild = columnRefTableFeild;
    }

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

    public String getColumnRefTableFeild() {
        return this.columnRefTableFeild;
    }

    public void setColumnRefTableFeild(String columnRefTableFeild) {
        this.columnRefTableFeild = columnRefTableFeild;
    }
}

