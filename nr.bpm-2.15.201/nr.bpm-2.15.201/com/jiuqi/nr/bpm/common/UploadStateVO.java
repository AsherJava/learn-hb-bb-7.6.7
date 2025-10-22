/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.upload.UploadState;

public class UploadStateVO {
    private String entiryId;
    private String period;
    private String formKey;
    private UploadState state;

    public String getEntiryId() {
        return this.entiryId;
    }

    public void setEntiryId(String entiryId) {
        this.entiryId = entiryId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public UploadState getState() {
        return this.state;
    }

    public void setState(UploadState state) {
        this.state = state;
    }
}

