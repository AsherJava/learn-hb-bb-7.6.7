/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import java.util.ArrayList;
import java.util.List;

public class ExportEnumCheckResParam {
    private String taskKey;
    private String formSchemeKey;
    private String asyncTaskId;
    private String periodDimValue;
    private List<String> orgCodes = new ArrayList<String>();

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getPeriodDimValue() {
        return this.periodDimValue;
    }

    public void setPeriodDimValue(String periodDimValue) {
        this.periodDimValue = periodDimValue;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }
}

