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

@FeignClient(contextId="vaDataModelMaintainFeign", name="${feignClient.datamodelMgr.name}", path="${feignClient.datamodelMgr.path}", url="${feignClient.datamodelMgr.url}")
public interface DataModelMaintainFeign {
    @PostMapping(value={"/dataModel/maintain/binary/get"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> get(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/maintain/binary/listAll"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> listAll(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/maintain/binary/add"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> add(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/maintain/binary/update"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> update(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/maintain/binary/publish"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> publish(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/maintain/binary/getBizTypes"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> getBizTypes(@RequestBody byte[] var1);
}

