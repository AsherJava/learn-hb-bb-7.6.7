/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import java.util.List;

public class StatusBarConfig {
    private boolean showCurrUnit;
    private boolean showReportStatus;
    private boolean showTableReadOnly;
    private boolean cellLocation;
    private boolean dataCalc;
    private boolean fullScreenShow;
    private List<String> showAsyncTaskTypes;

    public boolean isShowCurrUnit() {
        return this.showCurrUnit;
    }

    public void setShowCurrUnit(boolean showCurrUnit) {
        this.showCurrUnit = showCurrUnit;
    }

    public boolean isShowReportStatus() {
        return this.showReportStatus;
    }

    public void setShowReportStatus(boolean showReportStatus) {
        this.showReportStatus = showReportStatus;
    }

    public boolean isShowTableReadOnly() {
        return this.showTableReadOnly;
    }

    public void setShowTableReadOnly(boolean showTableReadOnly) {
        this.showTableReadOnly = showTableReadOnly;
    }

    public boolean isCellLocation() {
        return this.cellLocation;
    }

    public void setCellLocation(boolean cellLocation) {
        this.cellLocation = cellLocation;
    }

    public boolean isDataCalc() {
        return this.dataCalc;
    }

    public void setDataCalc(boolean dataCalc) {
        this.dataCalc = dataCalc;
    }

    public boolean isFullScreenShow() {
        return this.fullScreenShow;
    }

    public void setFullScreenShow(boolean fullScreenShow) {
        this.fullScreenShow = fullScreenShow;
    }

    public List<String> getShowAsyncTaskTypes() {
        return this.showAsyncTaskTypes;
    }

    public void setShowAsyncTaskTypes(List<String> showAsyncTaskTypes) {
        this.showAsyncTaskTypes = showAsyncTaskTypes;
    }
}

