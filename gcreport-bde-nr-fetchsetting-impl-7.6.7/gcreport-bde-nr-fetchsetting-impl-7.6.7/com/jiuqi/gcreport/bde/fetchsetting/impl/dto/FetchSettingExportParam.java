/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import java.util.List;

public class FetchSettingExportParam {
    private String fetchSchemeId;
    private List<String> formKeyData;
    private String formSchemeId;
    private Boolean templateExportFlag;

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public List<String> getFormKeyData() {
        return this.formKeyData;
    }

    public void setFormKeyData(List<String> formKeyData) {
        this.formKeyData = formKeyData;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public Boolean getTemplateExportFlag() {
        return this.templateExportFlag;
    }

    public void setTemplateExportFlag(Boolean templateExportFlag) {
        this.templateExportFlag = templateExportFlag;
    }
}

