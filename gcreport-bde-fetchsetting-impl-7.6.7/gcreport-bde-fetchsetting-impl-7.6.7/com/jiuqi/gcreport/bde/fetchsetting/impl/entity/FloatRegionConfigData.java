/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

public class FloatRegionConfigData {
    private String fetchSchemeId;
    private String formId;
    private String formSchemeId;
    private String regionId;
    private String regionName;
    private String floatConfig;
    private String floatType;
    private String sheetName;
    private Integer order;
    private String optionalField;

    public FloatRegionConfigData() {
    }

    public FloatRegionConfigData(String fetchSchemeId, String formId, String formSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formId = formId;
        this.formSchemeId = formSchemeId;
    }

    public String getOptionalField() {
        return this.optionalField;
    }

    public void setOptionalField(String optionalField) {
        this.optionalField = optionalField;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getFloatConfig() {
        return this.floatConfig;
    }

    public void setFloatConfig(String floatConfig) {
        this.floatConfig = floatConfig;
    }

    public String getFloatType() {
        return this.floatType;
    }

    public void setFloatType(String floatType) {
        this.floatType = floatType;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}

