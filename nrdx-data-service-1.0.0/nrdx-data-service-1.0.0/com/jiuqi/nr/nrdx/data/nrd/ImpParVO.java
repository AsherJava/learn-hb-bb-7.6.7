/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.data.nrd;

import java.io.Serializable;

public class ImpParVO
implements Serializable {
    private static final long serialVersionUID = 2678045926551678959L;
    private String fileKey;
    private boolean doUpload;
    private boolean allowForceUpload;
    private String uploadDes;
    private String taskKey;
    private String formSchemeKey;
    private String periodValue;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public boolean isDoUpload() {
        return this.doUpload;
    }

    public void setDoUpload(boolean doUpload) {
        this.doUpload = doUpload;
    }

    public boolean isAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(boolean allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

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

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getUploadDes() {
        return this.uploadDes;
    }

    public void setUploadDes(String uploadDes) {
        this.uploadDes = uploadDes;
    }
}

