/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;

public class FailedSettingLog {
    private String sheetName;
    private String errorLog;
    public static final String ERROR_LOG_BR = "<br/>";
    public static final String ERROR_LOG_FORM_BR = "\n";

    public FailedSettingLog(String sheetName, String errorLog) {
        this.sheetName = sheetName;
        this.errorLog = errorLog;
    }

    public FailedSettingLog(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
        this.sheetName = excelRowFetchSettingVO.getSheetName();
        this.errorLog = String.format("\u8868\u3010%1$s\u3011\u7b2c%2$s\u884c\u914d\u7f6e\u5b58\u5728\u9519\u8bef,%3$s %4$S", excelRowFetchSettingVO.getSheetName(), excelRowFetchSettingVO.getRowNum(), excelRowFetchSettingVO.getErrorLog(), ERROR_LOG_BR);
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getErrorLog() {
        return this.errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}

