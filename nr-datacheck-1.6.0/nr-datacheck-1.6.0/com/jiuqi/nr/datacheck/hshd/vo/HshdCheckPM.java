/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.hshd.vo;

import java.util.List;

public class HshdCheckPM {
    private String runId;
    private String task;
    private String period;
    private String periodEntity;
    private String orgEntity;
    private String mcScheme;
    private String itemKey;
    private List<String> orgCode;
    private String formScheme;
    private String webTabName;
    private boolean detailed;
    private String config;

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getWebTabName() {
        return this.webTabName;
    }

    public void setWebTabName(String webTabName) {
        this.webTabName = webTabName;
    }

    public boolean isDetailed() {
        return this.detailed;
    }

    public void setDetailed(boolean detailed) {
        this.detailed = detailed;
    }

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(String periodEntity) {
        this.periodEntity = periodEntity;
    }

    public String getMcScheme() {
        return this.mcScheme;
    }

    public void setMcScheme(String mcScheme) {
        this.mcScheme = mcScheme;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public List<String> getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(List<String> orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }
}

