/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionSettingEO;
import java.util.List;
import java.util.Map;

public interface DimensionSettingService {
    public void saveDimensionSetting(List<DimensionSettingEO> var1);

    public Map<String, Object> getDimensionSettingMapping(FetchSettingCond var1);
}

