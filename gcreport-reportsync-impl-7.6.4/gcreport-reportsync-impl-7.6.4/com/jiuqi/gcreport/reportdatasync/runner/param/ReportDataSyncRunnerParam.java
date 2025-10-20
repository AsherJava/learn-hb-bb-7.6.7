/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.runner.param;

import java.util.List;

public class ReportDataSyncRunnerParam {
    private String taskId;
    private String mappingConfigKey;
    private String periodType;
    private String orgType;
    private String periodStr;
    private String orgCheckType;
    private List<String> selectedOrgIds;
    private String systemHookName;
    private String systemHookUrl;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMappingConfigKey() {
        return this.mappingConfigKey;
    }

    public void setMappingConfigKey(String mappingConfigKey) {
        this.mappingConfigKey = mappingConfigKey;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public List<String> getSelectedOrgIds() {
        return this.selectedOrgIds;
    }

    public void setSelectedOrgIds(List<String> selectedOrgIds) {
        this.selectedOrgIds = selectedOrgIds;
    }

    public String getOrgCheckType() {
        return this.orgCheckType;
    }

    public void setOrgCheckType(String orgCheckType) {
        this.orgCheckType = orgCheckType;
    }

    public String getSystemHookName() {
        return this.systemHookName;
    }

    public void setSystemHookName(String systemHookName) {
        this.systemHookName = systemHookName;
    }

    public String getSystemHookUrl() {
        return this.systemHookUrl;
    }

    public void setSystemHookUrl(String systemHookUrl) {
        this.systemHookUrl = systemHookUrl;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

