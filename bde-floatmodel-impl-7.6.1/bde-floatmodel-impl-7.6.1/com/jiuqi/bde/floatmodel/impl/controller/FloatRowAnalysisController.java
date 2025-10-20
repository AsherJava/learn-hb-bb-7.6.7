/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.EncryptRequestDTO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient
 *  com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.floatmodel.impl.controller;

import com.jiuqi.bde.common.dto.EncryptRequestDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.floatmodel.client.FloatRowAnalysisClient;
import com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO;
import com.jiuqi.bde.floatmodel.impl.gather.FloatRegionHandlerGather;
import com.jiuqi.bde.floatmodel.impl.service.FloatRegionHandlerService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class FloatRowAnalysisController
implements FloatRowAnalysisClient {
    @Autowired
    private FloatRegionHandlerGather floatRegionHandlerGather;
    @Autowired
    private FloatRegionHandlerService floatRegionHandlerService;

    @GetMapping(value={"/api/bde/v1/floatRegion/listAllFloatRegionHandler"})
    public BusinessResponseEntity<List<FloatRegionHandlerVO>> listAllFloatRegionHandler() {
        return BusinessResponseEntity.ok(this.floatRegionHandlerService.getAllFloatRegionHandler());
    }

    @GetMapping(value={"/api/bde/v1/floatRegion/listAllFloatRegionHandlerAppInfo"})
    public BusinessResponseEntity<List<FloatRegionHandlerVO>> listAllFloatRegionHandlerAppInfo() {
        return BusinessResponseEntity.ok(this.floatRegionHandlerService.listAllFloatRegionHandlerAppInfo());
    }

    @PostMapping(value={"/api/bde/v1/floatRegion/parseFloatRowFields"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> parseFloatRowFields(@RequestBody EncryptRequestDTO<FloatRegionConfigVO> fetchFloatSettingDto) {
        Assert.isNotNull(fetchFloatSettingDto, (String)"\u6d6e\u52a8\u8bbe\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchFloatSettingDto.getParams(), (String)"\u6d6e\u52a8\u8bbe\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        FloatRegionConfigVO fetchFloatSettingVO = (FloatRegionConfigVO)fetchFloatSettingDto.parseParam(FloatRegionConfigVO.class);
        Assert.isNotNull((Object)fetchFloatSettingVO, (String)"\u6d6e\u52a8\u8bbe\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)fetchFloatSettingVO.getQueryType(), (String)"\u6d6e\u52a8\u8bbe\u7f6e\u4e2d\u7684\u67e5\u8be2\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        return BusinessResponseEntity.ok(this.floatRegionHandlerGather.getFloatRegionHandlerByQueryType(fetchFloatSettingVO.getQueryType()).parseFloatRowFields(fetchFloatSettingVO.getQueryConfigInfo()));
    }
}

