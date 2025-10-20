/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.log.LogDO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.feign.client;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.log.LogDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="vaLogClient", name="${feignClient.vaLog.name}", path="${feignClient.vaLog.path}", url="${feignClient.vaLog.url}")
public interface VaLogClient {
    @PostMapping(value={"/log/add"})
    public R add(@RequestBody LogDO var1);
}

