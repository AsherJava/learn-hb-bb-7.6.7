/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.FetchDimensionClient
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.bizmodel.sdk.feign;

import com.jiuqi.bde.bizmodel.client.FetchDimensionClient;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchDimensionFeign
implements FetchDimensionClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<List<DimensionVO>> listDimension() {
        FetchDimensionClient dynamicClient = (FetchDimensionClient)this.requestCertifyService.getFeignClient(FetchDimensionClient.class);
        return dynamicClient.listDimension();
    }

    public BusinessResponseEntity<List<DimensionVO>> listDimensionByDataModel(String bizDataModel) {
        FetchDimensionClient dynamicClient = (FetchDimensionClient)this.requestCertifyService.getFeignClient(FetchDimensionClient.class);
        return dynamicClient.listDimensionByDataModel(bizDataModel);
    }

    public BusinessResponseEntity<List<DimensionVO>> listAllDimensionByDataModel(String bizDataModel) {
        FetchDimensionClient dynamicClient = (FetchDimensionClient)this.requestCertifyService.getFeignClient(FetchDimensionClient.class);
        return dynamicClient.listAllDimensionByDataModel(bizDataModel);
    }

    public BusinessResponseEntity<List<DimensionVO>> listAllDimension() {
        FetchDimensionClient dynamicClient = (FetchDimensionClient)this.requestCertifyService.getFeignClient(FetchDimensionClient.class);
        return dynamicClient.listAllDimension();
    }

    public BusinessResponseEntity<List<SelectOptionVO>> listAllSelectOptionVO() {
        FetchDimensionClient dynamicClient = (FetchDimensionClient)this.requestCertifyService.getFeignClient(FetchDimensionClient.class);
        return dynamicClient.listAllSelectOptionVO();
    }
}

