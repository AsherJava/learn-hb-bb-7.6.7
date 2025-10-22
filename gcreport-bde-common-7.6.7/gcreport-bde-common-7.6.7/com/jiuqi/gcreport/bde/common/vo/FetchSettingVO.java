/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 */
package com.jiuqi.gcreport.bde.common.vo;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import java.util.List;

public class FetchSettingVO {
    private String id;
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String dataLinkId;
    private String dataLinkName;
    private String fieldDefineId;
    private Integer fieldDefineType;
    private String regionType;
    private List<FixedAdaptSettingVO> fixedSettingData;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFieldDefineId() {
        return this.fieldDefineId;
    }

    public void setFieldDefineId(String fieldDefineId) {
        this.fieldDefineId = fieldDefineId;
    }

    public Integer getFieldDefineType() {
        return this.fieldDefineType;
    }

    public void setFieldDefineType(Integer fieldDefineType) {
        this.fieldDefineType = fieldDefineType;
    }

    public String getRegionType() {
        return this.regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getDataLinkName() {
        return this.dataLinkName;
    }

    public void setDataLinkName(String dataLinkName) {
        this.dataLinkName = dataLinkName;
    }

    public List<FixedAdaptSettingVO> getFixedSettingData() {
        return this.fixedSettingData;
    }

    public void setFixedSettingData(List<FixedAdaptSettingVO> fixedSettingData) {
        this.fixedSettingData = fixedSettingData;
    }
}

