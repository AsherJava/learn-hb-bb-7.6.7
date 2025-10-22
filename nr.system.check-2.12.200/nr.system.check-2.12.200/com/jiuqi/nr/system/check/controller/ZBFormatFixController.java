/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.system.check.common.Util;
import com.jiuqi.nr.system.check.service.ZBFormatFixService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system-check/format-fix"})
@Api(tags={"\u7cfb\u7edf\u68c0\u67e5"})
public class ZBFormatFixController {
    @Autowired
    private ZBFormatFixService zbFormatChangeService;

    @GetMapping(value={"/dataScheme"})
    @ApiOperation(value="\u4fee\u6539\u6570\u636e\u65b9\u6848\u7684\u6307\u6807\u767e\u5206\u6bd4\u683c\u5f0f")
    public void fixDataSchemeFormat() throws JQException {
        try {
            this.zbFormatChangeService.fixDataSchemeZB();
        }
        catch (Exception e) {
            throw Util.getError("10000", String.format("\u4fee\u6539\u5931\u8d25{%s}", e.getMessage()));
        }
    }

    @GetMapping(value={"/task"})
    @ApiOperation(value="\u4fee\u6539\u4efb\u52a1\u7684\u94fe\u63a5\u767e\u5206\u6bd4\u683c\u5f0f")
    public void fixTaskFormat() throws JQException {
        try {
            this.zbFormatChangeService.fixTaskLink();
        }
        catch (Exception e) {
            throw Util.getError("10000", String.format("\u4fee\u6539\u5931\u8d25{%s}", e.getMessage()));
        }
    }
}

