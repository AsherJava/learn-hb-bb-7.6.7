/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.mobile.approval.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.mobile.approval.client.CnoocMobileClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@Api(value="/api/v1/cnooc", tags={"\u6d77\u6cb9\u4e2a\u6027\u63a5\u53e3"})
public interface CnoocMobileClient {
    @GetMapping(value={"/api/v1/cnooc/getEncodeUrl"})
    @ApiOperation(value="\u83b7\u53d6\u52a0\u5bc6\u540e\u7684url")
    public String getEncodeUrl(@RequestParam(value="url") String var1);
}

