/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.instance.bean;

import com.jiuqi.nr.bpm.setting.pojo.CustomPeriodData;
import java.util.List;

public class WorkflowBaseInfoResult {
    private String taskKey;
    private String period;
    private String formSchemeKey;
    private List<CustomPeriodData> customPeriodDataList;
    private String formPeriod;
    private String toPeriod;
    private String periodType;
    private String dwMainDim;
    private boolean showEditWorkflowButton;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<CustomPeriodData> getCustomPeriodDataList() {
        return this.customPeriodDataList;
    }

    public void setCustomPeriodDataList(List<CustomPeriodData> customPeriodDataList) {
        this.customPeriodDataList = customPeriodDataList;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormPeriod() {
        return this.formPeriod;
    }

    public void setFormPeriod(String formPeriod) {
        this.formPeriod = formPeriod;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getDwMainDim() {
        return this.dwMainDim;
    }

    public void setDwMainDim(String dwMainDim) {
        this.dwMainDim = dwMainDim;
    }

    public boolean isShowEditWorkflowButton() {
        return this.showEditWorkflowButton;
    }

    public void setShowEditWorkflowButton(boolean showEditWorkflowButton) {
        this.showEditWorkflowButton = showEditWorkflowButton;
    }
}

