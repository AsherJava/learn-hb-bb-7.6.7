/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

public class FloatDimensionSettingEO {
    public static final String TABLENAME = "BDE_DIMENSIONSETTING";
    private static final long serialVersionUID = -96101823777915226L;
    private String id;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String dimensionConfigInfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return "FloatDimensionSettingEO{id='" + this.id + '\'' + ", formSchemeId='" + this.formSchemeId + '\'' + ", formId='" + this.formId + '\'' + ", regionId='" + this.regionId + '\'' + ", dimensionConfigInfo='" + this.dimensionConfigInfo + '\'' + '}';
    }
}

