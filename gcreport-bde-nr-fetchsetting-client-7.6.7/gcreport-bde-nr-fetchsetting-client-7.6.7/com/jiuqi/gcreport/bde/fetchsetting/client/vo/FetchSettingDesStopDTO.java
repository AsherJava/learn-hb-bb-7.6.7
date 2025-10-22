/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import java.util.List;
import java.util.Map;

public class FetchSettingDesStopDTO {
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private Map<String, List<String>> regionLinkIds;

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

    public Map<String, List<String>> getRegionLinkIds() {
        return this.regionLinkIds;
    }

    public void setRegionLinkIds(Map<String, List<String>> regionLinkIds) {
        this.regionLinkIds = regionLinkIds;
    }
}

