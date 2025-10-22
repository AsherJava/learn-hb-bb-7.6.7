/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import java.util.List;

public class FixedFieldDefineSettingDTO {
    private String id;
    private String regionId;
    private String dataLinkId;
    private String fieldDefineId;
    private String fieldDefineTitle;
    private Integer fieldDefineType;
    private Integer fieldDefineSize;
    private Integer fieldDefineFractionDigits;
    private List<FixedAdaptSettingDTO> fixedSettingData;
    private Integer stopFlag;
    private Integer sortOrder;

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

    public String getFieldDefineTitle() {
        return this.fieldDefineTitle;
    }

    public void setFieldDefineTitle(String fieldDefineTitle) {
        this.fieldDefineTitle = fieldDefineTitle;
    }

    public Integer getFieldDefineType() {
        return this.fieldDefineType;
    }

    public Integer getFieldDefineSize() {
        return this.fieldDefineSize;
    }

    public void setFieldDefineSize(Integer fieldDefineSize) {
        this.fieldDefineSize = fieldDefineSize;
    }

    public Integer getFieldDefineFractionDigits() {
        return this.fieldDefineFractionDigits;
    }

    public void setFieldDefineFractionDigits(Integer fieldDefineFractionDigits) {
        this.fieldDefineFractionDigits = fieldDefineFractionDigits;
    }

    public void setFieldDefineType(Integer fieldDefineType) {
        this.fieldDefineType = fieldDefineType;
    }

    public List<FixedAdaptSettingDTO> getFixedSettingData() {
        return this.fixedSettingData;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setFixedSettingData(List<FixedAdaptSettingDTO> fixedSettingData) {
        this.fixedSettingData = fixedSettingData;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

