/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.gcreport.mobile.approval.client;

import com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataReturnInfo;
import com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(contextId="com.jiuqi.gcreport.mobile.approval.client.FormDataQueryClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@Api(value="/api/v1/simple", tags={"\u7b80\u5355\u67e5\u8be2\u62a5\u8868\u6570\u636e"})
public interface FormDataQueryClient {
    @RequestMapping(value={"/api/v1/simple/formDataQuery"}, method={RequestMethod.POST})
    @ApiOperation(value="\u7b80\u5355\u67e5\u8be2\u62a5\u8868\u6570\u636e")
    @ResponseBody
    public FormDataReturnInfo formDataQuery(@RequestBody FormDataQueryInfo var1);

    @GetMapping(value={"/api/v1/simple/preview/{fileKey}"})
    @ApiOperation(value="\u9644\u4ef6\u9884\u89c8")
    public void previewFile(@PathVariable String var1, HttpServletResponse var2);

    @PostMapping(value={"/api/v1/simple/queryWorkflowDataInfo"})
    @ApiOperation(value=" \u67e5\u8be2\u4e0a\u62a5\u9001\u5ba1\u6309\u94ae")
    public ActionDataReturnInfo queryWorkflowDataInfo(@RequestBody QueryParamInfo var1);
}

