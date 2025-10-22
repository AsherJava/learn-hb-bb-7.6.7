/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionValueSettingEO;
import java.util.List;
import java.util.Map;

public interface DimensionValueSettingService {
    public void saveDimensionValueSetting(List<DimensionValueSettingEO> var1);

    public Map<String, Object> getDimensionValueSettingMapping(FetchSettingCond var1);
}

