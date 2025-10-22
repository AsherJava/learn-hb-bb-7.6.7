/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingDesStopDTO;

public interface FetchSettingDesStopService {
    public void disableFetchSetting(FetchSettingDesStopDTO var1);

    public void enableFetchSetting(FetchSettingDesStopDTO var1);

    public FloatRegionConfigVO getFloatRegionConfigVODesWithStopState(FetchSettingCond var1);

    public FloatRegionConfigVO getFloatRegionConfigVO(FetchSettingCond var1);
}

