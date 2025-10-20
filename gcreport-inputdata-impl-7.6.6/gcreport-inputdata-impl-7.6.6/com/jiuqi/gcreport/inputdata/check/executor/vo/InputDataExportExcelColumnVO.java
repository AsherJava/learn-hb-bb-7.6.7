/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.check.executor.vo;

import java.util.List;

public class InputDataExportExcelColumnVO {
    private List<String> allTitleColumns;
    private List<String> amtColumns;
    private List<String> titleKeys;

    public List<String> getAllTitleColumns() {
        return this.allTitleColumns;
    }

    public void setAllTitleColumns(List<String> allTitleColumns) {
        this.allTitleColumns = allTitleColumns;
    }

    public List<String> getAmtColumns() {
        return this.amtColumns;
    }

    public void setAmtColumns(List<String> amtColumns) {
        this.amtColumns = amtColumns;
    }

    public List<String> getTitleKeys() {
        return this.titleKeys;
    }

    public void setTitleKeys(List<String> titleKeys) {
        this.titleKeys = titleKeys;
    }
}

