/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import java.util.List;

public interface FetchFloatSettingService {
    public List<FloatRegionConfigVO> listFetchFloatSettingByFormId(FetchSettingCond var1);

    public FloatRegionConfigVO listFetchFloatSettingByRegionId(FetchSettingCond var1);

    public FloatRegionConfigVO getFetchFloatSetting(FetchSettingCond var1);

    public List<String> listFormKeyBySchemeKey(String var1, String var2);

    public void fetchFloatSettingCacheClear();

    public void fetchFloatSettingCacheEvit(String var1);

    public void addBatch(List<FetchFloatSettingEO> var1);

    public void deleteByFetchSchemeId(String var1);

    public void deleteFloatFetchSettingByFetchSettingCond(FetchSettingCond var1);

    public List<FetchFloatSettingEO> getFetchFloatSettingListByCond(FetchSettingCond var1);
}

