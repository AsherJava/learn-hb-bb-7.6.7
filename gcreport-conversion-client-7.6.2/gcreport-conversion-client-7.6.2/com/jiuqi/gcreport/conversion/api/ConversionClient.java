/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.gcreport.conversion.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="com.jiuqi.gcreport.conversion.api.ConversionClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConversionClient {
    public static final String CONVERSION_SYSTEM_API_BASE_PATH = "/api/gcreport/v1/conversion";

    @GetMapping(value={"/api/gcreport/v1/conversion/getConversionInitEnv"})
    public BusinessResponseEntity<ConversionInitEnvDTO> getConversionInitEnv();
}

