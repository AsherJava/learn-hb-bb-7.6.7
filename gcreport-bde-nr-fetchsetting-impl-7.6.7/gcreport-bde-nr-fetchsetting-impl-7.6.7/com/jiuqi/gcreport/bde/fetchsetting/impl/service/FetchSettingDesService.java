/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import java.util.List;
import java.util.Map;

public interface FetchSettingDesService {
    public String queryFormTitle(String var1);

    public FixedFieldDefineSettingDTO listDataLinkFixedSettingDesRowRecords(FetchSettingCond var1);

    public List<FixedFieldDefineSettingDTO> listDataLinkFixedSettingDes(FetchSettingListLinkCond var1);

    public String saveFetchFixedSettingDataHandle(FetchSettingSaveDataVO var1);

    public Map<String, List<FetchSettingVO>> getFetchSettingDesGroupByDataLinkId(FetchSettingCond var1);

    public Map<String, List<FetchSettingVO>> getFetchFloatSettingDesGroupByDataLinkId(FetchSettingCond var1);

    public Map<String, Object> listDataLinkByRegionId(String var1);

    public void saveBatchFetchFixedSettingDes(List<FetchSettingDesEO> var1);

    public void deleteBatchFetchFloatSettingDes(FetchSettingCond var1);

    public FixedFieldDefineSettingVO convertFixedDefineSettingByFetchSettingDesEOList(List<FetchSettingDesEO> var1);

    public List<Map<String, Object>> listFixedSettingDesByForm(FetchSettingCond var1);

    public List<FetchSettingVO> listFetchSettingDesByFetchSchemeId(FetchSettingCond var1);

    public List<FetchSettingVO> listFetchSettingDesByCondi(FetchSettingCond var1);

    public List<FixedFieldDefineSettingVO> listFixedFieldDefineSettingDesByFormId(FetchSettingCond var1);
}

