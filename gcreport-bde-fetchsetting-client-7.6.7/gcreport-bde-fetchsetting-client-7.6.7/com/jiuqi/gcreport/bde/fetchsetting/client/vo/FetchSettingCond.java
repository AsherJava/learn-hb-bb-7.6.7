/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

public class FetchSettingCond {
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String dataLinkId;

    public FetchSettingCond() {
    }

    public FetchSettingCond(String fetchSchemeId, String formSchemeId, String formId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formSchemeId = formSchemeId;
        this.formId = formId;
    }

    public FetchSettingCond(String fetchSchemeId, String formSchemeId, String formId, String regionId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formSchemeId = formSchemeId;
        this.formId = formId;
        this.regionId = regionId;
    }

    public FetchSettingCond(String fetchSchemeId, String formSchemeId, String formId, String regionId, String dataLinkId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formSchemeId = formSchemeId;
        this.formId = formId;
        this.regionId = regionId;
        this.dataLinkId = dataLinkId;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
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

    public String getDataLinkId() {
        return this.dataLinkId;
    }

    public void setDataLinkId(String dataLinkId) {
        this.dataLinkId = dataLinkId;
    }
}

