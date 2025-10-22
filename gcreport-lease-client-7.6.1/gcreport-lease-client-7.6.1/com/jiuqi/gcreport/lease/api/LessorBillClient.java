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
package com.jiuqi.gcreport.lease.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.leasebill.api.LessorBillClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface LessorBillClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/lessorBill";

    @PostMapping(value={"/api/gcreport/v1/lessorBill/list"})
    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listLessorBills(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/lessorBill/batchDelete"})
    public BusinessResponseEntity<String> batchDelete(@RequestBody List<String> var1);
}

