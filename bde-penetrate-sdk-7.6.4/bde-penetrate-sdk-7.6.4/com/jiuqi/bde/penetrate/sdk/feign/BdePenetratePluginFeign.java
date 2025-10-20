/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.penetrate.client.PenetratePluginClient
 *  com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate
 *  com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.penetrate.sdk.feign;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.penetrate.client.PenetratePluginClient;
import com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate;
import com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BdePenetratePluginFeign
implements PenetratePluginClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<PenetrateOffsetedDate> offsetPeriodAndYear(@RequestBody PenetratePeriodOffsetDTO periodOffsetCondi) {
        PenetratePluginClient dynamicClient = (PenetratePluginClient)this.requestCertifyService.getFeignClient(PenetratePluginClient.class);
        return dynamicClient.offsetPeriodAndYear(periodOffsetCondi);
    }

    public BusinessResponseEntity<Map<String, Map<String, String>>> queryFetchSettingInfo(@RequestBody FetchSettingInfoDTO fetchSettingInfo) {
        PenetratePluginClient dynamicClient = (PenetratePluginClient)this.requestCertifyService.getFeignClient(PenetratePluginClient.class);
        return dynamicClient.queryFetchSettingInfo(fetchSettingInfo);
    }

    public BusinessResponseEntity<Map<String, Map<String, String>>> queryBaseDataInfo(@RequestBody PenetrateFetchSettingInfo fetchSettingInfo) {
        PenetratePluginClient dynamicClient = (PenetratePluginClient)this.requestCertifyService.getFeignClient(PenetratePluginClient.class);
        return dynamicClient.queryBaseDataInfo(fetchSettingInfo);
    }
}

