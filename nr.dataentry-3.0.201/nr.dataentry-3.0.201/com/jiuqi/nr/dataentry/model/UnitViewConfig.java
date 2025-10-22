/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

public class UnitViewConfig {
    private String title;
    private String[] showTitle;
    private String[] showCode;
    private String[] showReportStatus;
    private String[] showKeyZb;
    private boolean hideUnitMenu;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String[] showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isHideUnitMenu() {
        return this.hideUnitMenu;
    }

    public void setHideUnitMenu(boolean hideUnitMenu) {
        this.hideUnitMenu = hideUnitMenu;
    }

    public String[] getShowCode() {
        if (this.showCode == null) {
            return new String[0];
        }
        return this.showCode;
    }

    public void setShowCode(String[] showCode) {
        this.showCode = showCode;
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

    public String[] getShowKeyZb() {
        if (this.showKeyZb == null) {
            return new String[0];
        }
        return this.showKeyZb;
    }

    public void setShowKeyZb(String[] showKeyZb) {
        this.showKeyZb = showKeyZb;
    }
}

