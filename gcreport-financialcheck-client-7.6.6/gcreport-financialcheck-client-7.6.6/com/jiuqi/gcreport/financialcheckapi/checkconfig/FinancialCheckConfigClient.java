/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.financialcheckapi.checkconfig;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.api.financialcheck.FinancialCheckConfigClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface FinancialCheckConfigClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/checkConfig";

    @PostMapping(value={"/api/gcreport/v1/checkConfig/save"})
    public BusinessResponseEntity<Object> save(@RequestBody FinancialCheckConfigVO var1);

    @PostMapping(value={"/api/gcreport/v1/checkConfig/query"})
    public BusinessResponseEntity<FinancialCheckConfigVO> query();
}

