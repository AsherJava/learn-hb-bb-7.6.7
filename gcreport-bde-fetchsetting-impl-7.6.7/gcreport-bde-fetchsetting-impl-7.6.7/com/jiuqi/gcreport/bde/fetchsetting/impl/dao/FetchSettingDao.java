/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import java.util.List;
import java.util.Set;

public interface FetchSettingDao {
    public List<FetchSettingEO> listEnableFetchSettingByFormId(String var1, String var2, String var3);

    public List<FetchSettingEO> listFetchSettingByFormId(String var1, String var2, String var3);

    public Set<String> listFormulaFieldId(String var1, String var2, String var3, String var4);

    public void deleteFetchSettingByFetchSettingCond(FetchSettingCond var1);

    public List<FetchSettingEO> listFetchSettingByDataLinkId(FetchSettingCond var1);

    public List<FetchSettingEO> listFetchSettingByRegionId(FetchSettingCond var1);

    public List<FetchSettingEO> loadAll();

    public void addBatch(List<FetchSettingEO> var1);

    public List<FetchSettingEO> listFetchSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);

    public void deleteFetchSettingData(List<String> var1);

    public void updateFetchSettingDk(List<FetchSettingVO> var1);

    public List<FetchSettingEO> listAllFloatFetchSetting();

    public void updateBatch(List<FetchSettingEO> var1);

    public List<String> listFormKeyBySchemeKey(String var1, String var2);

    public List<FetchSettingEO> listFetchSettingWithStopFlagByRegionId(FetchSettingCond var1);
}

