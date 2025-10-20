/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.BDEQueryDataCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import java.util.List;
import java.util.Map;

public interface FetchSettingService {
    public List<FetchSettingVO> listFetchSettingByFormId(FetchSettingCond var1);

    public List<FetchSettingEO> listAllFetchSettingEOByFormId(FetchSettingCond var1);

    public void fetchSettingPublishByFetchSchemeId(FetchSettingCond var1);

    public void fetchSettingPublishByFormId(FetchSettingCond var1);

    public List<FixedFieldDefineSettingDTO> listDataLinkFixedSettingRowRecords(FetchSettingCond var1);

    public Map<String, Double> getBDEQueryDataMapping(BDEQueryDataCond var1);

    public List<Map<String, Object>> getBDEFloatPenetrateTableData(BDEQueryDataCond var1);

    public GcFetchRequestDTO getBDEPenetrateParam(BDEQueryDataCond var1);

    public Map<String, Double> getBDEFloatPenetrateResultByRanks(BDEQueryDataCond var1);

    public List<String> lisFormKeyBySchemeKey(String var1, String var2);

    public void deleteByFetchSchemeId(String var1);

    public void addBatch(List<FetchSettingEO> var1);

    public void fetchSettingCacheClear();

    public void fetchSettingCacheEvictInFetchScheme(String var1, String var2);

    public void fetchSettingCacheEvict(String var1, String var2);

    public void fetchSettingCacheEvictInFetchScheme(String var1, List<String> var2);

    public List<FetchSettingEO> getFetchSettingListByCond(FetchSettingCond var1);

    public List<FixedAdaptSettingVO> convertFixedSettingDataStr(String var1);
}

