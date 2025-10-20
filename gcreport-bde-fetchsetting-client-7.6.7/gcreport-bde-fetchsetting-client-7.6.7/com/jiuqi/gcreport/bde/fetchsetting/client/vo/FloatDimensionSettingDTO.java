/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

public class FloatDimensionSettingDTO {
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String dimensionConfigInfo;

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
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

    public String getDimensionConfigInfo() {
        return this.dimensionConfigInfo;
    }

    public void setDimensionConfigInfo(String dimensionConfigInfo) {
        this.dimensionConfigInfo = dimensionConfigInfo;
    }

    public String toString() {
        return "FloatDimensionSettingDTO{formSchemeId='" + this.formSchemeId + '\'' + ", formId='" + this.formId + '\'' + ", regionId='" + this.regionId + '\'' + ", dimensionConfigInfo='" + this.dimensionConfigInfo + '\'' + '}';
    }
}

