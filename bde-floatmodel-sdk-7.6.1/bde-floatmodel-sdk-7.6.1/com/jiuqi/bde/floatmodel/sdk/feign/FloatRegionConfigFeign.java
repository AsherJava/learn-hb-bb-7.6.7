/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.floatmodel.client.FloatRegionConfigClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.floatmodel.sdk.feign;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.floatmodel.client.FloatRegionConfigClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloatRegionConfigFeign
implements FloatRegionConfigClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    @GetMapping(value={"/api/gcreport/v1//floatRegion/vaQuery/tree/init"})
    public BusinessResponseEntity<List<MenuTreeVO>> treeInit() {
        FloatRegionConfigClient dynamicClient = (FloatRegionConfigClient)this.requestCertifyService.getFeignClient(FloatRegionConfigClient.class);
        return dynamicClient.treeInit();
    }

    @GetMapping(value={"/api/gcreport/v1//floatRegion/vaQuery/getQueryParams/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryParams(@PathVariable(value="templateCode") String templateCode) {
        FloatRegionConfigClient dynamicClient = (FloatRegionConfigClient)this.requestCertifyService.getFeignClient(FloatRegionConfigClient.class);
        return dynamicClient.getQueryParams(templateCode);
    }
}

