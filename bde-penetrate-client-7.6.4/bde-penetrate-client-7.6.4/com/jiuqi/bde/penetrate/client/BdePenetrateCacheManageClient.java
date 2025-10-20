/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.bde.penetrate.client;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.bde.penetrate.client.BdePenetrateCacheManageClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface BdePenetrateCacheManageClient {
    public static final String API_PATH = "/api/bde/v1/penetrate/cache";

    @GetMapping(value={"/api/bde/v1/penetrate/cache/get/{id}"})
    public BusinessResponseEntity<EncryptRequestDTO<String>> getPenetrateContext(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/bde/v1/penetrate/cache/save"})
    public BusinessResponseEntity<String> savePenetrateContext(@RequestBody EncryptRequestDTO<String> var1);
}

