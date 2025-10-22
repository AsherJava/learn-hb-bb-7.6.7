/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.gcreport.financialcheckapi.offset.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckapi.offset.web.FinancialCheckRuleClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FinancialCheckRuleClient {
    @GetMapping(value={"/api/gcreport/v1/unionrules/fc/offsetGroupingFields"})
    public BusinessResponseEntity<List<Map<String, String>>> getOffsetGroupingField();
}

