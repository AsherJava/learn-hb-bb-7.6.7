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
package com.jiuqi.bde.fetch.client;

import com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.bde.fetch.client.FetchFormulaClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface FetchFormulaClient {
    public static final String API = "/api/bde/v1/fetch/formula";

    @PostMapping(value={"/api/bde/v1/fetch/formula/check"})
    public BusinessResponseEntity<Boolean> check(@RequestBody FetchFormulaDTO var1);
}

