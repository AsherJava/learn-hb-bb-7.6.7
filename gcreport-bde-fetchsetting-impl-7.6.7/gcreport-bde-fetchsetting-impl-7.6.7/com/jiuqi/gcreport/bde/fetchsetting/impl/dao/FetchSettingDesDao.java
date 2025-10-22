/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FetchSettingDesDao {
    public List<FetchSettingDesEO> listFetchSettingDesByCondi(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFetchSettingDesByRegionId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFetchSettingDesWithStopFlagByRegionId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFetchSettingDesByDataLinkId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listDataLinkFixedSettingDes(FetchSettingListLinkCond var1);

    public void deleteBatchFetchSettingDesData(List<List<Object>> var1);

    public void deleteFloatFetchSettingDesData(List<List<Object>> var1);

    public Set<String> listFormulaFieldId(String var1, String var2, String var3, String var4);

    public void deleteBatchFetchSettingDesDataByRegionId(List<List<Object>> var1);

    public List<FetchSettingDesEO> listFixedFetchSettingDesByFormId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFixedFetchFloatSettingDesByFormId(FetchSettingCond var1);

    public int getFetchSettingDesByFetchSourceCode(String var1);

    public List<FetchSettingDesEO> listFetchSettingDesByFetchSchemeId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFetchSettingDesWithStopFlagByFetchSchemeId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFetchSettingDesByFormId(FetchSettingCond var1);

    public List<FetchSettingDesEO> listFetchSettingDesWithStopFlagByFormId(FetchSettingCond var1);

    public List<FetchSettingDesEO> loadAll();

    public void addBatch(List<FetchSettingDesEO> var1);

    public List<FetchSettingDesEO> listFetchSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);

    public void deleteFetchSettingDesData(List<String> var1);

    public void updateFetchSettingDesDk(List<FetchSettingVO> var1);

    public List<FetchSettingDesEO> listAllFloatFetchSetting();

    public void updateBatch(List<FetchSettingDesEO> var1);

    public void deleteFetchSettingByFetchSettingCond(FetchSettingCond var1, List<String> var2);

    public Map<String, Integer> getFixSettingDesStopFlag(FetchSettingCond var1, List<String> var2);

    public void disableFixSetting(FetchSettingCond var1, List<String> var2);

    public void enableFixSetting(FetchSettingCond var1, List<String> var2);
}

