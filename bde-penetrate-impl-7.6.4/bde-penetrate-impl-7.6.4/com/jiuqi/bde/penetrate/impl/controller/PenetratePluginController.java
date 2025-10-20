/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.PenetratePluginClient
 *  com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate
 *  com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.penetrate.impl.controller;

import com.jiuqi.bde.penetrate.client.PenetratePluginClient;
import com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate;
import com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO;
import com.jiuqi.bde.penetrate.impl.service.BdePenetrateService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PenetratePluginController
implements PenetratePluginClient {
    @Autowired
    private BdePenetrateService penetrateService;

    public BusinessResponseEntity<PenetrateOffsetedDate> offsetPeriodAndYear(PenetratePeriodOffsetDTO periodOffsetCondi) {
        return BusinessResponseEntity.ok((Object)this.penetrateService.offsetPeriodAndYear(periodOffsetCondi));
    }

    public BusinessResponseEntity<Map<String, Map<String, String>>> queryFetchSettingInfo(FetchSettingInfoDTO fetchSettingInfo) {
        return BusinessResponseEntity.ok(this.penetrateService.queryFetchSettingInfo(fetchSettingInfo));
    }

    public BusinessResponseEntity<Map<String, Map<String, String>>> queryBaseDataInfo(PenetrateFetchSettingInfo fetchSettingInfo) {
        return BusinessResponseEntity.ok(this.penetrateService.queryBaseDataInfo(fetchSettingInfo));
    }
}

