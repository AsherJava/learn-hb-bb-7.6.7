/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.fetch.client.FetchFormulaClient
 *  com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.fetch.sdk.feign;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.fetch.client.FetchFormulaClient;
import com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchFormulaFeign
implements FetchFormulaClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<Boolean> check(@RequestBody FetchFormulaDTO fetchFormula) {
        FetchFormulaClient dynamicClient = (FetchFormulaClient)this.requestCertifyService.getFeignClient(FetchFormulaClient.class);
        return dynamicClient.check(fetchFormula);
    }
}

