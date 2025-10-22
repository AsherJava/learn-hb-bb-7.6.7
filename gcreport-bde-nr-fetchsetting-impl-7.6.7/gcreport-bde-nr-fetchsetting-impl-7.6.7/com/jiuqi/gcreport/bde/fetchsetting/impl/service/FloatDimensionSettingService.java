/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.DimensionSetting;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO;
import java.util.List;

public interface FloatDimensionSettingService {
    public List<DimensionSetting> getDimensionSetByRegionId(String var1);

    public void saveFloatDimensionSetting(FloatDimensionSettingDTO var1);

    public List<String> getDimensionSettingFiledList(String var1);
}

