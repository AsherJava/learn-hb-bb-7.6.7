/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.nr.common.importdata.ImportResultReportObject;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import java.io.Serializable;

public class ImportResultSheetObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sheetName;
    private ResultErrorInfo sheetError;
    private ImportResultReportObject importResultReportObject;

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public ImportResultReportObject getImportResultReportObject() {
        return this.importResultReportObject;
    }

    public void setImportResultReportObject(ImportResultReportObject importResultReportObject) {
        this.importResultReportObject = importResultReportObject;
    }

    public ResultErrorInfo getSheetError() {
        if (null == this.sheetError) {
            this.sheetError = new ResultErrorInfo();
        }
        return this.sheetError;
    }

    public void setSheetError(ResultErrorInfo sheetError) {
        this.sheetError = sheetError;
    }
}

