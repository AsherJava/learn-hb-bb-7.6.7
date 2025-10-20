/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import java.util.List;

public class ExcelSettingDTO {
    private String formId;
    private String regionId;
    private List<ExcelRowFetchSettingVO> rowFetchSettings;
    private FloatRegionConfigVO floatRegionConfigVO;

    public List<ExcelRowFetchSettingVO> getExportFetchSourceRowImpSettingVOS() {
        return this.rowFetchSettings;
    }

    public void setExportFetchSourceRowImpSettingVOS(List<ExcelRowFetchSettingVO> excelRowFetchSettingVOS) {
        this.rowFetchSettings = excelRowFetchSettingVOS;
    }

    public FloatRegionConfigVO getFloatRegionConfigVO() {
        return this.floatRegionConfigVO;
    }

    public void setFloatRegionConfigVO(FloatRegionConfigVO floatRegionConfigVO) {
        this.floatRegionConfigVO = floatRegionConfigVO;
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
}

