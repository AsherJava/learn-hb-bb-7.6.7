/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.floatmodel.sdk.feign;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloatRowAnalysisFeign
implements FloatRowAnalysisClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<List<FloatRegionHandlerVO>> listAllFloatRegionHandler() {
        FloatRowAnalysisClient dynamicClient = (FloatRowAnalysisClient)this.requestCertifyService.getFeignClient(FloatRowAnalysisClient.class);
        return dynamicClient.listAllFloatRegionHandler();
    }

    public BusinessResponseEntity<List<FloatRegionHandlerVO>> listAllFloatRegionHandlerAppInfo() {
        FloatRowAnalysisClient dynamicClient = (FloatRowAnalysisClient)this.requestCertifyService.getFeignClient(FloatRowAnalysisClient.class);
        return dynamicClient.listAllFloatRegionHandlerAppInfo();
    }

    public BusinessResponseEntity<List<FetchQueryFiledVO>> parseFloatRowFields(EncryptRequestDTO<FloatRegionConfigVO> fetchFloatSettingDto) {
        FloatRowAnalysisClient dynamicClient = (FloatRowAnalysisClient)this.requestCertifyService.getFeignClient(FloatRowAnalysisClient.class);
        return dynamicClient.parseFloatRowFields(fetchFloatSettingDto);
    }
}

