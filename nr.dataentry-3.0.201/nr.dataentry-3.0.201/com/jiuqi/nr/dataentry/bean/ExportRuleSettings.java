/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.io.Serializable;
import java.util.List;

public class ExportRuleSettings
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> excelName;
    private List<String> sheetName;
    private List<String> zipName;
    private String separatorCode;
    private boolean simplifyExportFileHierarchy;

    public List<String> getExcelName() {
        return this.excelName;
    }

    public void setExcelName(List<String> excelName) {
        this.excelName = excelName;
    }

    public List<String> getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(List<String> sheetName) {
        this.sheetName = sheetName;
    }

    public List<String> getZipName() {
        return this.zipName;
    }

    public void setZipName(List<String> zipName) {
        this.zipName = zipName;
    }

    public String getSeparatorCode() {
        return this.separatorCode;
    }

    public void setSeparatorCode(String separatorCode) {
        this.separatorCode = separatorCode;
    }

    public boolean isSimplifyExportFileHierarchy() {
        return this.simplifyExportFileHierarchy;
    }

    public void setSimplifyExportFileHierarchy(boolean simplifyExportFileHierarchy) {
        this.simplifyExportFileHierarchy = simplifyExportFileHierarchy;
    }
}

