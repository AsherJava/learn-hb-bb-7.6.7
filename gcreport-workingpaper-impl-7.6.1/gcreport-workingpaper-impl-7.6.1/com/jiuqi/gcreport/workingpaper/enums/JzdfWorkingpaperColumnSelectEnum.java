/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.enums;

public enum JzdfWorkingpaperColumnSelectEnum {
    ENDDATADETAILS("endDataDetails", "\u671f\u672b\u6570\u660e\u7ec6"),
    SUMENDDATA("sumEndData", "\u671f\u672b\u6570\u6c47\u603b"),
    OFFSETDATADETAILS("offsetDataDetails", "\u62b5\u9500\u6570\u660e\u7ec6"),
    SUMOFFSETDATA("sumOffsetData", "\u62b5\u9500\u6570\u6c47\u603b"),
    COMBINEDDATA("combinedData", "\u5408\u5e76\u6570");

    private String columnCode;
    private String columnTitle;

    public String getColumnCode() {
        return this.columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getColumnTitle() {
        return this.columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    private JzdfWorkingpaperColumnSelectEnum(String columnCode, String columnTitle) {
        this.columnCode = columnCode;
        this.columnTitle = columnTitle;
    }

    public static JzdfWorkingpaperColumnSelectEnum getEnumByValue(String columnCode) {
        for (JzdfWorkingpaperColumnSelectEnum columnSelectEnum : JzdfWorkingpaperColumnSelectEnum.values()) {
            if (columnSelectEnum.getColumnCode() != columnCode) continue;
            return columnSelectEnum;
        }
        return null;
    }
}

