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
package com.jiuqi.gcreport.financialcheckapi.dataentry.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckapi.dataentry.api.FcIncrementCollectClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FcIncrementCollectClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/fcIncrementData";

    @PostMapping(value={"/api/gcreport/v1/fcIncrementData/incrementCollect"})
    public BusinessResponseEntity<CheckResult> doIncrementDataCollect(@RequestBody List<Map<String, Object>> var1);
}

