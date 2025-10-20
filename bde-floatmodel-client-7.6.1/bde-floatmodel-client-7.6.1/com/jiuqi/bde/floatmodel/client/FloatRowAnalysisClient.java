/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.bde.floatmodel.client;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface FloatRowAnalysisClient {
    public static final String FLOAT_API = "/api/bde/v1/floatRegion";

    @GetMapping(value={"/api/bde/v1/floatRegion/listAllFloatRegionHandler"})
    public BusinessResponseEntity<List<FloatRegionHandlerVO>> listAllFloatRegionHandler();

    @GetMapping(value={"/api/bde/v1/floatRegion/listAllFloatRegionHandlerAppInfo"})
    public BusinessResponseEntity<List<FloatRegionHandlerVO>> listAllFloatRegionHandlerAppInfo();

    @PostMapping(value={"/api/bde/v1/floatRegion/parseFloatRowFields"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> parseFloatRowFields(@RequestBody EncryptRequestDTO<FloatRegionConfigVO> var1);
}

