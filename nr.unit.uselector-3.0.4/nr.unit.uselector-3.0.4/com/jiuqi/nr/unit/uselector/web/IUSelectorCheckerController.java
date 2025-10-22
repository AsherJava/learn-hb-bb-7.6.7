/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.unit.uselector.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.unit.uselector.web.request.FilteringRequestParam;
import com.jiuqi.nr.unit.uselector.web.response.FilteringInfo;
import com.jiuqi.nr.unit.uselector.web.service.IUSelectorCheckerService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/unit-selector/checker"})
@Api(tags={"\u5355\u4f4d\u9009\u62e9\u5668-\u5355\u4f4d\u7b5b\u9009API"})
public class IUSelectorCheckerController {
    @Resource
    private IUSelectorCheckerService service;

    @NRContextBuild
    @ResponseBody
    @PostMapping(value={"/append-filtering"})
    @ApiOperation(value="\u8ffd\u52a0\u7b5b\u9009\u6761\u4ef6-\u6267\u884c\u5355\u4f4d\u7b5b\u9009")
    public FilteringInfo appendFiltering(@Valid @RequestBody @SFDecrypt FilteringRequestParam reParam) {
        return this.service.executeFiltering(reParam);
    }
}

