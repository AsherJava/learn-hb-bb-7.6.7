/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.investbill.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient(contextId="com.jiuqi.gcreport.investbill.api.FormSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface FairValueBillClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/fvBill";

    @GetMapping(value={"/api/gcreport/v1/fvBill/queryFvchBillCode/{investBillId}/{periodStr}"})
    public BusinessResponseEntity<Map<String, Object>> queryFvchBillCode(@PathVariable(value="investBillId") String var1, @PathVariable(value="periodStr") String var2);
}

