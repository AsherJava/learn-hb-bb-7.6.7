/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionFetchSetting {
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private Map<String, List<FixedAdaptSettingExcelDTO>> fixeAdaptSettingDTOMap;
    private Map<String, String> dataLinkTitleMap;
    private String sheetName;
    private FloatRegionConfigVO floatRegionConfigVO;

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

    public FloatRegionConfigVO getFloatRegionConfigVO() {
        return this.floatRegionConfigVO;
    }

    public void setFloatRegionConfigVO(FloatRegionConfigVO floatRegionConfigVO) {
        this.floatRegionConfigVO = floatRegionConfigVO;
    }

    public Map<String, List<FixedAdaptSettingExcelDTO>> getFixeAdaptSettingDTOMap() {
        return this.fixeAdaptSettingDTOMap;
    }

    public void setFixeAdaptSettingDTOMap(Map<String, List<FixedAdaptSettingExcelDTO>> fixeAdaptSettingDTOMap) {
        this.fixeAdaptSettingDTOMap = fixeAdaptSettingDTOMap;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Map<String, String> getDataLinkTitleMap() {
        return this.dataLinkTitleMap;
    }

    public void setDataLinkTitleMap(Map<String, String> dataLinkTitleMap) {
        this.dataLinkTitleMap = dataLinkTitleMap;
    }
}

