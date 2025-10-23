/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.parameter;

import java.util.List;

public class BuildParam {
    private String taskKey;
    private String reportSchemeKey;
    private String currPeriod;
    private List<String> currUnits;
    private boolean showDw;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getReportSchemeKey() {
        return this.reportSchemeKey;
    }

    public void setReportSchemeKey(String reportSchemeKey) {
        this.reportSchemeKey = reportSchemeKey;
    }

    public String getCurrPeriod() {
        return this.currPeriod;
    }

    public void setCurrPeriod(String currPeriod) {
        this.currPeriod = currPeriod;
    }

    public List<String> getCurrUnits() {
        return this.currUnits;
    }

    public void setCurrUnits(List<String> currUnits) {
        this.currUnits = currUnits;
    }

    public boolean isShowDw() {
        return this.showDw;
    }

    public void setShowDw(boolean showDw) {
        this.showDw = showDw;
    }
}

