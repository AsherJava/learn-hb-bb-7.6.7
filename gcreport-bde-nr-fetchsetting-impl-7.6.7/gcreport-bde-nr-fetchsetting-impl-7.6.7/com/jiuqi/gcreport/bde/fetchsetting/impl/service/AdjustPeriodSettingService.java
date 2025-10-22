/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodFetchDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import java.util.List;

public interface AdjustPeriodSettingService {
    public void save(List<AdjustPeriodSettingVO> var1, String var2);

    public List<AdjustPeriodSettingVO> listAdjustPeriodSettingByFetchSchemeId(String var1);

    public Boolean isAdjustFetch(AdjustPeriodFetchDTO var1);

    public void deleteByFetchSchemeId(String var1);

    public void cleanCache();

    public void copyAdjustPeriodSettingByFetchSchemeId(String var1, String var2);
}

