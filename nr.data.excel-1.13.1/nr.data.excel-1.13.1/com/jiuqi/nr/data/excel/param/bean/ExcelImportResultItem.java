/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param.bean;

import com.jiuqi.nr.data.excel.param.bean.ImportResultItem;
import java.io.Serializable;

public class ExcelImportResultItem
implements ImportResultItem,
Serializable {
    private static final long serialVersionUID = 1L;
    private String formName;
    private String sheetName;
    private String fileName;
    private String errorInfo;
    private String errorCode;

    public ExcelImportResultItem() {
    }

    public ExcelImportResultItem(String formName, String sheetName, String fileName, String errorInfo, String errorCode) {
        this.fileName = fileName;
        this.formName = formName;
        this.sheetName = sheetName;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getErrorInfo() {
        return this.errorInfo;
    }

    @Override
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

