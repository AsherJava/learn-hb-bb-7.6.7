/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.leasebill.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.leasebill.api.TenantryBillClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface TenantryBillClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/tenantryBill";

    @PostMapping(value={"/api/gcreport/v1/tenantryBill/list"})
    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listTenantryBills(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/tenantryBill/batchDelete"})
    public BusinessResponseEntity<String> batchDelete(@RequestBody List<String> var1);
}

