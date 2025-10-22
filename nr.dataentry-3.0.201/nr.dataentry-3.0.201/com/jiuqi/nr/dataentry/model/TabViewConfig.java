/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

public class TabViewConfig {
    private String title;
    private String groupStyle;
    private String reportTabStyle;
    private String[] showTableCode;
    private String[] markTable;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupStyle() {
        return this.groupStyle;
    }

    public void setGroupStyle(String groupStyle) {
        this.groupStyle = groupStyle;
    }

    public String getReportTabStyle() {
        return this.reportTabStyle;
    }

    public void setReportTabStyle(String reportTabStyle) {
        this.reportTabStyle = reportTabStyle;
    }

    public String[] getShowTableCode() {
        return this.showTableCode;
    }

    public void setShowTableCode(String[] showTableCode) {
        this.showTableCode = showTableCode;
    }

    public String[] getMarkTable() {
        if (this.markTable == null) {
            return new String[0];
        }
        return this.markTable;
    }

    public void setMarkTable(String[] markTable) {
        this.markTable = markTable;
    }
}

