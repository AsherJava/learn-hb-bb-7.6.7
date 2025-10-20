/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.CrossFormSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.DataLinkDimValueSettingVO;
import java.util.List;
import java.util.Map;

public class GeneratingFetchSettingVO {
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private Map<String, List<DataLinkDimValueSettingVO>> regionSetting;
    private CrossFormSettingVO crossFormSettingData;

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

    public Map<String, List<DataLinkDimValueSettingVO>> getRegionSetting() {
        return this.regionSetting;
    }

    public void setRegionSetting(Map<String, List<DataLinkDimValueSettingVO>> regionSetting) {
        this.regionSetting = regionSetting;
    }

    public CrossFormSettingVO getCrossFormSettingData() {
        return this.crossFormSettingData;
    }

    public void setCrossFormSettingData(CrossFormSettingVO crossFormSettingData) {
        this.crossFormSettingData = crossFormSettingData;
    }
}

