/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.investcarryover.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.carryover.api.InvestBillCarryOverClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestBillCarryOverClient {
    public static final String CARRYOVER_API_BASE_PATH = "/api/gcreport/v1/investBillCarryOver";

    @PostMapping(value={"/api/gcreport/v1/investBillCarryOver/doInvestCarryOver"})
    public BusinessResponseEntity<Object> doInvestCarryOver(@RequestBody QueryParamsVO var1);
}

