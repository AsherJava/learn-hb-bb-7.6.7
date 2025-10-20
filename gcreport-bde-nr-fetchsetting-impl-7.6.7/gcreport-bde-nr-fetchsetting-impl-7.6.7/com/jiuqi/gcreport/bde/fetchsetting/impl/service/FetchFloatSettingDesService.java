/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import java.util.List;
import java.util.Map;

public interface FetchFloatSettingDesService {
    public FloatRegionConfigVO getFetchFloatSettingDes(FetchSettingCond var1);

    public List<FloatRegionConfigVO> listFloatSettingDesByCondi(FetchSettingCond var1);

    public List<FloatRegionConfigVO> listFetchFloatSettingDesByFetchScheme(FetchSettingCond var1);

    public Map<String, FloatRegionConfigVO> getFloatRegionSettingDesByForm(FetchSettingCond var1);

    public String saveFetchFloatSettingDataHandle(FetchSettingSaveDataVO var1);

    public List<SelectOptionVO> listOtherEntity(String var1);

    public void cleanFloatSetting(FetchSettingCond var1);
}

