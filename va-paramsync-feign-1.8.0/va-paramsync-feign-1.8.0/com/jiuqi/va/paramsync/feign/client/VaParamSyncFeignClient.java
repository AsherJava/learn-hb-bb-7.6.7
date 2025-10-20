/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RequestPart
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.paramsync.feign.client;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncParamDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(primary=false, contextId="vaParamSyncClient", name="${feignClient.paramsync.name}", path="${feignClient.paramsync.path}", url="${feignClient.paramsync.url}")
public interface VaParamSyncFeignClient {
    @PostMapping(value={"/paramsync/export"})
    public VaParamSyncResponseDO export(@RequestBody VaParamSyncParamDO var1);

    @PostMapping(value={"/paramsync/import/groups"})
    public R getImportGroups(@RequestBody VaParamSyncMainfestDO var1, @RequestParam(value="metaType") String var2);

    @PostMapping(value={"/paramsync/import"}, consumes={"multipart/form-data"})
    public R importParam(@RequestPart(name="multipartFile") MultipartFile var1, @RequestPart(name="params") String var2);
}

