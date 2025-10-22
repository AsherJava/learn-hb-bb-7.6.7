/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.relation.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.relation.api.InvestRelationClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestRelationClient {
    public static final String API_PATH = "/api/gcreport/v1/relation";

    @PostMapping(value={"/api/gcreport/v1/relation/getInvestRelationList"})
    public BusinessResponseEntity<Object> getInvestRelationList(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/relation/getInvestRelation"})
    public BusinessResponseEntity<Object> getInvestRelation(@RequestBody Map<String, Object> var1);
}

