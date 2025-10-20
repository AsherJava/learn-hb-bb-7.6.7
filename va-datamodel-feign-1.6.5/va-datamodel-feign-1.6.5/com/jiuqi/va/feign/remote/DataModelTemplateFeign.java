/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.feign.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="vaDataModelTemplateFeign", name="${feignClient.datamodelMgr.name}", path="${feignClient.datamodelMgr.path}", url="${feignClient.datamodelMgr.url}")
public interface DataModelTemplateFeign {
    @PostMapping(value={"/dataModel/template/binary/get"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> getDataModelTemplate(@RequestBody byte[] var1);
}

