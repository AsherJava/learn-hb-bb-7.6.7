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

@FeignClient(contextId="vaDataModelFeign", name="${feignClient.datamodelMgr.name}", path="${feignClient.datamodelMgr.path}", url="${feignClient.datamodelMgr.url}")
public interface DataModelFeign {
    @PostMapping(value={"/dataModel/binary/get"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> get(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/list"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> list(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/update"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> push(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/updateBaseInfo"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> updateBaseInfo(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/updateComplete"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> pushComplete(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/updateIncrement"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> pushIncrement(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/remove"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> remove(@RequestBody byte[] var1);

    @PostMapping(value={"/dataModel/binary/sync/cache"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> syncCache(@RequestBody byte[] var1);
}

