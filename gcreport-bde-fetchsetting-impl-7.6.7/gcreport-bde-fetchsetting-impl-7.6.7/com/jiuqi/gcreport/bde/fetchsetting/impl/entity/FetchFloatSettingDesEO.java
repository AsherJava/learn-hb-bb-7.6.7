/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

public class FetchFloatSettingDesEO {
    public static final String TABLENAME = "BDE_FETCHFLOATSETTING_DES";
    private static final long serialVersionUID = -2401334588932222858L;
    private String id;
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String queryType;
    private String queryConfigInfo;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryConfigInfo() {
        return this.queryConfigInfo;
    }

    public void setQueryConfigInfo(String queryConfigInfo) {
        this.queryConfigInfo = queryConfigInfo;
    }
}

