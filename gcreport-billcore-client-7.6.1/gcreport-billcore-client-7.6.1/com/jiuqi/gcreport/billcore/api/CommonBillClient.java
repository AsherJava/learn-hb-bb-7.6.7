/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.billcore.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.common.api.CommonBillClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface CommonBillClient {
    public static final String API_PATH = "/api/gcreport/v1/commonBill";

    @GetMapping(value={"/api/gcreport/v1/commonBill/listColumn/{tableName}/{defineCode}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listColumns(@PathVariable String var1, @PathVariable String var2);

    @GetMapping(value={"/api/gcreport/v1/commonBill/getOrgType/{defineCode}"})
    public BusinessResponseEntity<String> getOrgType(@PathVariable String var1);

    @GetMapping(value={"/api/gcreport/v1/commonBill/getAcctYearRange"})
    public List<Map<String, String>> listAcctYearRange();

    @GetMapping(value={"/api/gcreport/v1/commonBill/getOrgTypeByTableName/{tableName}"})
    public BusinessResponseEntity<String> getOrgTypeByTableName(@PathVariable String var1);
}

