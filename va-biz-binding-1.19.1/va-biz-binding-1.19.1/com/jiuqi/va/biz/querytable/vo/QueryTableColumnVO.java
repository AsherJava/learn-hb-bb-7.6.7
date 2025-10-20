/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.vo;

public class QueryTableColumnVO {
    private String columnName;
    private String columnTitle;

    public QueryTableColumnVO() {
    }

    public QueryTableColumnVO(String columnName, String columnTitle) {
        this.columnName = columnName;
        this.columnTitle = columnTitle;
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
}

