/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

public class UnBindResult {
    private String formSchemeKey;
    private String period;
    private String unitId;
    private String reportId;
    private int sType;
    private String dimEntityKey;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getReportId() {
        return this.reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public int getsType() {
        return this.sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public String getDimEntityKey() {
        return this.dimEntityKey;
    }

    public void setDimEntityKey(String dimEntityKey) {
        this.dimEntityKey = dimEntityKey;
    }
}

