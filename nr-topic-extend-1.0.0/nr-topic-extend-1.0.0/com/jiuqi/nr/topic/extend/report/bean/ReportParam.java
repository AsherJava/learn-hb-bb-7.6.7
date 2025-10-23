/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.topic.extend.report.bean;

public class ReportParam {
    private String formKey;
    private String org;
    private String period;
    private String orgEntity;
    private String lastOrg;
    private String lastPeriod;
    private boolean periodJson;
    private boolean orgJson;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }

    public String getLastOrg() {
        return this.lastOrg;
    }

    public void setLastOrg(String lastOrg) {
        this.lastOrg = lastOrg;
    }

    public String getLastPeriod() {
        return this.lastPeriod;
    }

    public void setLastPeriod(String lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public boolean isPeriodJson() {
        return this.periodJson;
    }

    public void setPeriodJson(boolean periodJson) {
        this.periodJson = periodJson;
    }

    public boolean isOrgJson() {
        return this.orgJson;
    }

    public void setOrgJson(boolean orgJson) {
        this.orgJson = orgJson;
    }
}

