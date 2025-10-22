/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import java.util.LinkedHashMap;
import java.util.Map;

public class FetchSettingSaveDataVO {
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private LinkedHashMap<String, LinkedHashMap<String, FixedFieldDefineSettingDTO>> fixedSettingDatas;
    private Map<String, FloatRegionConfigVO> fetchFloatSettingDatas;

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

    public LinkedHashMap<String, LinkedHashMap<String, FixedFieldDefineSettingDTO>> getFixedSettingDatas() {
        return this.fixedSettingDatas;
    }

    public void setFixedSettingDatas(LinkedHashMap<String, LinkedHashMap<String, FixedFieldDefineSettingDTO>> fixedSettingDatas) {
        this.fixedSettingDatas = fixedSettingDatas;
    }

    public Map<String, FloatRegionConfigVO> getFetchFloatSettingDatas() {
        return this.fetchFloatSettingDatas;
    }

    public void setFetchFloatSettingDatas(Map<String, FloatRegionConfigVO> fetchFloatSettingDatas) {
        this.fetchFloatSettingDatas = fetchFloatSettingDatas;
    }
}

