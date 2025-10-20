/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate
 *  com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO
 */
package com.jiuqi.bde.penetrate.impl.service;

import com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate;
import com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO;
import java.util.Map;

public interface BdePenetrateService {
    public PenetrateOffsetedDate offsetPeriodAndYear(PenetratePeriodOffsetDTO var1);

    public Map<String, Map<String, String>> queryFetchSettingInfo(FetchSettingInfoDTO var1);

    public Map<String, Map<String, String>> queryBaseDataInfo(PenetrateFetchSettingInfo var1);
}

