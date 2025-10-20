/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.penetrate.client.BdePenetrateCacheManageClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.penetrate.sdk.feign;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.penetrate.client.BdePenetrateCacheManageClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BdePenetrateCacheManageFeign
implements BdePenetrateCacheManageClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<EncryptRequestDTO<String>> getPenetrateContext(@PathVariable(value="id") String id) {
        BdePenetrateCacheManageClient dynamicClient = (BdePenetrateCacheManageClient)this.requestCertifyService.getFeignClient(BdePenetrateCacheManageClient.class);
        return dynamicClient.getPenetrateContext(id);
    }

    public BusinessResponseEntity<String> savePenetrateContext(@RequestBody EncryptRequestDTO<String> context) {
        BdePenetrateCacheManageClient dynamicClient = (BdePenetrateCacheManageClient)this.requestCertifyService.getFeignClient(BdePenetrateCacheManageClient.class);
        return dynamicClient.savePenetrateContext(context);
    }
}

