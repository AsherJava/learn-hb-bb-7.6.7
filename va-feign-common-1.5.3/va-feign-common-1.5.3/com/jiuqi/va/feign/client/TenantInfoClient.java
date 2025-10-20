/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.va.feign.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(primary=false, contextId="vaTenantInfoClient", name="${feignClient.tenantInfo.name}", path="${feignClient.tenantInfo.path}", url="${feignClient.tenantInfo.url}")
public interface TenantInfoClient {
    @PostMapping(value={"/anon/tenant/name/list"})
    public List<String> nameList();
}

