/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto.fetch.query;

public class FetchTaskInfoQueryDTO {
    private String asyncTaskId;
    private String formId;
    private String regionId;

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String toString() {
        return "FetchTaskInfoQueryDTO{asyncTaskId='" + this.asyncTaskId + '\'' + ", formId='" + this.formId + '\'' + ", regionId='" + this.regionId + '\'' + '}';
    }
}

