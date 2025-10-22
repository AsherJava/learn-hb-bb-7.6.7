/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import java.util.List;

public class FetchSettingListLinkCond {
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private List<String> dataLinkIdList;

    public FetchSettingListLinkCond() {
    }

    public FetchSettingListLinkCond(String fetchSchemeId, String formSchemeId, String formId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formSchemeId = formSchemeId;
        this.formId = formId;
    }

    public FetchSettingListLinkCond(String fetchSchemeId, String formSchemeId, String formId, String regionId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formSchemeId = formSchemeId;
        this.formId = formId;
        this.regionId = regionId;
    }

    public FetchSettingListLinkCond(String fetchSchemeId, String formSchemeId, String formId, String regionId, List<String> dataLinkId) {
        this.fetchSchemeId = fetchSchemeId;
        this.formSchemeId = formSchemeId;
        this.formId = formId;
        this.regionId = regionId;
        this.dataLinkIdList = dataLinkId;
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

    public List<String> getDataLinkIdList() {
        return this.dataLinkIdList;
    }

    public void setDataLinkIdList(List<String> dataLinkIdList) {
        this.dataLinkIdList = dataLinkIdList;
    }
}

