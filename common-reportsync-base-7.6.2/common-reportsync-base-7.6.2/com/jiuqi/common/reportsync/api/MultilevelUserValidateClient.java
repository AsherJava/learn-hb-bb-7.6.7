/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.common.reportsync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.reportsync.dto.MultilevelCheckParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.common.reportsync.api.ReportSyncCheckClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface MultilevelUserValidateClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/multileveSync";

    @PostMapping(value={"/api/gcreport/v1/multileveSync/userCheck"})
    public BusinessResponseEntity<String> checkPassword(@RequestBody MultilevelCheckParam var1);
}

