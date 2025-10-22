/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

@Deprecated
public class StatusViewConfig {
    private String title;
    private String[] showCurrUnit;
    private String[] showCurrTable;
    private String[] showReportStatus;
    private String[] showTableReadOnly;
    private String[] dataEditStatus;
    private String[] showDimension;
    private String[] cellLocation;
    private String[] cellProp;
    private String[] cellFormula;
    private String[] dataCalc;
    private String[] fullScreenShow;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getShowCurrUnit() {
        if (this.showCurrUnit == null) {
            return new String[0];
        }
        return this.showCurrUnit;
    }

    public void setShowCurrUnit(String[] showCurrUnit) {
        this.showCurrUnit = showCurrUnit;
    }

    public String[] getShowCurrTable() {
        if (this.showCurrTable == null) {
            return new String[0];
        }
        return this.showCurrTable;
    }

    public void setShowCurrTable(String[] showCurrTable) {
        this.showCurrTable = showCurrTable;
    }

    public String[] getShowReportStatus() {
        if (this.showReportStatus == null) {
            return new String[0];
        }
        return this.showReportStatus;
    }

    public void setShowReportStatus(String[] showReportStatus) {
        this.showReportStatus = showReportStatus;
    }

    public String[] getShowTableReadOnly() {
        if (this.showTableReadOnly == null) {
            return new String[0];
        }
        return this.showTableReadOnly;
    }

    public void setShowTableReadOnly(String[] showTableReadOnly) {
        this.showTableReadOnly = showTableReadOnly;
    }

    public String[] getDataEditStatus() {
        if (this.dataEditStatus == null) {
            return new String[0];
        }
        return this.dataEditStatus;
    }

    public void setDataEditStatus(String[] dataEditStatus) {
        this.dataEditStatus = dataEditStatus;
    }

    public String[] getShowDimension() {
        if (this.showDimension == null) {
            return new String[0];
        }
        return this.showDimension;
    }

    public void setShowDimension(String[] showDimension) {
        this.showDimension = showDimension;
    }

    public String[] getCellLocation() {
        if (this.cellLocation == null) {
            return new String[0];
        }
        return this.cellLocation;
    }

    public void setCellLocation(String[] cellLocation) {
        this.cellLocation = cellLocation;
    }

    public String[] getCellProp() {
        if (this.cellProp == null) {
            return new String[0];
        }
        return this.cellProp;
    }

    public void setCellProp(String[] cellProp) {
        this.cellProp = cellProp;
    }

    public String[] getCellFormula() {
        if (this.cellFormula == null) {
            return new String[0];
        }
        return this.cellFormula;
    }

    public void setCellFormula(String[] cellFormula) {
        this.cellFormula = cellFormula;
    }

    public String[] getDataCalc() {
        if (this.dataCalc == null) {
            return new String[0];
        }
        return this.dataCalc;
    }

    public void setDataCalc(String[] dataCalc) {
        this.dataCalc = dataCalc;
    }

    public String[] getFullScreenShow() {
        if (this.fullScreenShow == null) {
            return new String[0];
        }
        return this.fullScreenShow;
    }

    public void setFullScreenShow(String[] fullScreenShow) {
        this.fullScreenShow = fullScreenShow;
    }
}

