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
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import java.util.List;
import java.util.Map;

public interface FetchFloatSettingDesDao {
    public List<FetchFloatSettingDesEO> listFloatSettingDesByCondi(FetchSettingCond var1);

    public FetchFloatSettingDesEO listFloatSettingDesByRegionId(FetchSettingCond var1);

    public FetchFloatSettingDesEO getFetchFloatSettingDes(FetchSettingCond var1);

    public void deleteBatchFetchFloatSettingDesData(List<List<Object>> var1);

    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByFetchSchemeId(FetchSettingCond var1);

    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByFormId(FetchSettingCond var1);

    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByBillUniqueCodeAndFetchSchemeId(FetchSettingCond var1);

    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByFetchScheme(FetchSettingCond var1);

    public void addBatch(List<FetchFloatSettingDesEO> var1);

    public List<FetchFloatSettingDesEO> loadAll();

    public List<FetchFloatSettingDesEO> listFetchSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);

    public void deleteFloatSettingDesData(List<String> var1);

    public void updateFloatSettingDesData(List<FloatRegionConfigVO> var1);

    public void updateFloatRegionConfigVOStopFlag(String var1, String var2);

    public void batchUpdateQueryConfigInfoById(Map<String, String> var1);
}

