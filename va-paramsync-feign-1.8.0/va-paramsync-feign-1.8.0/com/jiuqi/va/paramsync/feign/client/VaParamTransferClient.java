/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.paramsync.feign.client;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="vaParamTransferClient", name="${feignClient.paramtransfer.name}", path="${feignClient.paramtransfer.path}", url="${feignClient.paramtransfer.url}")
public interface VaParamTransferClient {
    @PostMapping(value={"/paramtransfer/regist/module"})
    public R registParamTransferModule(@RequestBody TenantDO var1);
}

