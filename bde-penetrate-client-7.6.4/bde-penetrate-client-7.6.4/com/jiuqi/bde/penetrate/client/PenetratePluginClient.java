/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.bde.penetrate.client;

import com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate;
import com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.bde.penetrate.client.PenetratePluginCollectClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface PenetratePluginClient {
    public static final String API_PATH = "/api/bde/v1/penetrate";

    @PostMapping(value={"/api/bde/v1/penetrate/main/processPeriodAndYearOffset"})
    public BusinessResponseEntity<PenetrateOffsetedDate> offsetPeriodAndYear(@RequestBody PenetratePeriodOffsetDTO var1);

    @PostMapping(value={"/api/bde/v1/penetrate/queryFetchSettingInfo"})
    public BusinessResponseEntity<Map<String, Map<String, String>>> queryFetchSettingInfo(@RequestBody FetchSettingInfoDTO var1);

    @PostMapping(value={"/api/bde/v1/penetrate/queryBaseDataInfo"})
    public BusinessResponseEntity<Map<String, Map<String, String>>> queryBaseDataInfo(@RequestBody PenetrateFetchSettingInfo var1);
}

