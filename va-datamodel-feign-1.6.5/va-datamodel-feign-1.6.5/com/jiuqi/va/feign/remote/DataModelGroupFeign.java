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

@FeignClient(contextId="vaDataModelGroupFeign", name="${feignClient.datamodelMgr.name}", path="${feignClient.datamodelMgr.path}", url="${feignClient.datamodelMgr.url}")
public interface DataModelGroupFeign {
    @PostMapping(value={"/dataModel/defineGroup/binary/external/get"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> get(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/defineGroup/binary/external/list"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> list(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/defineGroup/binary/external/add"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> add(@RequestBody byte[] var1);
}

