/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.consts;

import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatRegionConfigData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import java.util.List;

public class FloatFetchSourceRowSettingExportVO {
    private List<ExcelRowFetchSettingVO> setting;
    private FloatRegionConfigData floatRegionConfigData;

    public List<ExcelRowFetchSettingVO> getSetting() {
        return this.setting;
    }

    public void setSetting(List<ExcelRowFetchSettingVO> setting) {
        this.setting = setting;
    }

    public FloatRegionConfigData getFloatRegionConfigData() {
        return this.floatRegionConfigData;
    }

    public void setFloatRegionConfigData(FloatRegionConfigData floatRegionConfigData) {
        this.floatRegionConfigData = floatRegionConfigData;
    }
}

