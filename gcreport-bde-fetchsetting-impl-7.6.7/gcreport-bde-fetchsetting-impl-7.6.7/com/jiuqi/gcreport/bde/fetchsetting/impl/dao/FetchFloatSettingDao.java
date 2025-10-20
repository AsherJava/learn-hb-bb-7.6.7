/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import java.util.List;
import java.util.Map;

public interface FetchFloatSettingDao {
    public List<FetchFloatSettingEO> listFetchFloatSettingByFormId(FetchSettingCond var1);

    public void deleteFloatFetchSettingByFetchSettingCond(FetchSettingCond var1);

    public FetchFloatSettingEO getFetchFloatSetting(FetchSettingCond var1);

    public List<FetchFloatSettingEO> loadAll();

    public void addBatch(List<FetchFloatSettingEO> var1);

    public List<FetchFloatSettingEO> listFetchSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);

    public void updateFloatSettingData(List<FloatRegionConfigVO> var1);

    public void deleteFloatSettingData(List<String> var1);

    public void batchUpdateQueryConfigInfoById(Map<String, String> var1);

    public List<String> lisFormKeyBySchemeKey(String var1, String var2);
}

