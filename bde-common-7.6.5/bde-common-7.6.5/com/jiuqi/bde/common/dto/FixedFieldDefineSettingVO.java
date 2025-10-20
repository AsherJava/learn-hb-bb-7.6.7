/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import java.util.List;

public class FixedFieldDefineSettingVO {
    private String id;
    private String regionId;
    private String dataLinkId;
    private String fieldDefineId;
    private String fieldDefineTitle;
    private Integer fieldDefineType;
    private Integer fieldDefineSize;
    private Integer fieldDefineFractionDigits;
    private List<FixedAdaptSettingVO> fixedSettingData;

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

    public List<FixedAdaptSettingVO> getFixedSettingData() {
        return this.fixedSettingData;
    }

    public void setFixedSettingData(List<FixedAdaptSettingVO> fixedSettingData) {
        this.fixedSettingData = fixedSettingData;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

