/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.gcreport.financialcheckapi.datatrace.web;

import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckapi.datatrace.web.FinancialCheckDataTraceClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FinancialCheckDataTraceClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/fc/datatrace";

    @PostMapping(value={"/api/gcreport/v1/fc/datatrace/listOffsetAndSourceItem"})
    public Object listOffsetAndSourceItem(Map<String, Object> var1);
}

