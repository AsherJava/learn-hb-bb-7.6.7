/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.coop.feign.client;

import com.jiuqi.coop.feign.config.domain.ClbrRecordDO;
import com.jiuqi.va.domain.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(primary=false, contextId="coopClient", name="${feignClient.coop.name}", path="${feignClient.coop.path}", url="${feignClient.coop.url}")
public interface CoopFeignClient {
    @PostMapping(value={"coop/getData/getListByClbrRecord"})
    public R getListByClbrRecord(ClbrRecordDO var1);
}

