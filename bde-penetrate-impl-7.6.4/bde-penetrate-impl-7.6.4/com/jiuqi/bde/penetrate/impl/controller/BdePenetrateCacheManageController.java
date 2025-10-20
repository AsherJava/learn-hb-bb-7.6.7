/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.penetrate.client.BdePenetrateCacheManageClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.penetrate.impl.controller;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.penetrate.client.BdePenetrateCacheManageClient;
import com.jiuqi.bde.penetrate.impl.service.BdePenetrateCacheManageService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BdePenetrateCacheManageController
implements BdePenetrateCacheManageClient {
    @Autowired
    BdePenetrateCacheManageService bdePenetrateCacheManageService;

    public BusinessResponseEntity<EncryptRequestDTO<String>> getPenetrateContext(@PathVariable(value="id") String id) {
        EncryptRequestDTO encodeValue = new EncryptRequestDTO((Object)this.bdePenetrateCacheManageService.getPenetrateContext(id));
        return BusinessResponseEntity.ok((Object)encodeValue);
    }

    public BusinessResponseEntity<String> savePenetrateContext(@RequestBody EncryptRequestDTO<String> context) {
        String parseParam = (String)context.parseParam(String.class);
        return BusinessResponseEntity.ok((Object)this.bdePenetrateCacheManageService.savePenetrateContext(parseParam));
    }
}

