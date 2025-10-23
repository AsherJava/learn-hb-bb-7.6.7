/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

public class ReleaseFormVO {
    private String formSchemeKey;
    private boolean formSchemeNotPublished;
    private String dataSchemeKey;
    private boolean dataSchemeNotPublished;
    private boolean dataPrefixChanged;
    private boolean dataDimChanged;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean isFormSchemeNotPublished() {
        return this.formSchemeNotPublished;
    }

    public void setFormSchemeNotPublished(boolean formSchemeNotPublished) {
        this.formSchemeNotPublished = formSchemeNotPublished;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isDataSchemeNotPublished() {
        return this.dataSchemeNotPublished;
    }

    public void setDataSchemeNotPublished(boolean dataSchemeNotPublished) {
        this.dataSchemeNotPublished = dataSchemeNotPublished;
    }

    public boolean isDataPrefixChanged() {
        return this.dataPrefixChanged;
    }

    public void setDataPrefixChanged(boolean dataPrefixChanged) {
        this.dataPrefixChanged = dataPrefixChanged;
    }

    public boolean isDataDimChanged() {
        return this.dataDimChanged;
    }

    public void setDataDimChanged(boolean dataDimChanged) {
        this.dataDimChanged = dataDimChanged;
    }
}

