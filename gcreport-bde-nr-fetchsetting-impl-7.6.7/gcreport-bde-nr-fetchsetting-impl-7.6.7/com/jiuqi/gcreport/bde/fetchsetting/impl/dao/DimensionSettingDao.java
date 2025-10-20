/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionSettingEO;
import java.util.List;

public interface DimensionSettingDao {
    public void deleteBatchDimensionSetting(List<List<Object>> var1);

    public List<DimensionSettingEO> listDimensionSetting(FetchSettingCond var1);

    public List<DimensionSettingEO> loadAll();

    public void addBatch(List<DimensionSettingEO> var1);

    public List<DimensionSettingEO> listFetchSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);
}

