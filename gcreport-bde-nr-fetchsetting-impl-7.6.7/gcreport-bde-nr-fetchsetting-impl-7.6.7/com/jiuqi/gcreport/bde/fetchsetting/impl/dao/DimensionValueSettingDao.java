/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionValueSettingEO;
import java.util.List;

public interface DimensionValueSettingDao {
    public void deleteBatchDimensionValueSetting(List<List<Object>> var1);

    public List<DimensionValueSettingEO> listDimensionSetting(FetchSettingCond var1);

    public List<DimensionValueSettingEO> loadAll();

    public void addBatch(List<DimensionValueSettingEO> var1);

    public List<DimensionValueSettingEO> listFetchSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);
}

