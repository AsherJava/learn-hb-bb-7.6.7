/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.workflow2.settings.message.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceBaseContext;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceQueryContext;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageInstanceStrategyVerifyContext;
import com.jiuqi.nr.workflow2.settings.message.service.MessageInstanceService;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageInstanceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping(value={"/nr/workflow2/settings/message-instance"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u6d88\u606f\u8bbe\u7f6e-\u6d88\u606f\u5b9e\u4f8b"})
public class MessageInstanceController {
    @Autowired
    private MessageInstanceService messageInstanceService;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e - \u6d88\u606f\u5b9e\u4f8b\u6570\u636e\u521d\u59cb\u5316")
    @PostMapping(value={"/init"})
    public MessageInstanceVO messageInstanceInit(@RequestBody @Valid MessageInstanceQueryContext context) {
        return this.messageInstanceService.messageInstanceInit(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e - \u6d88\u606f\u754c\u9762\u63a5\u6536\u4eba\u53c2\u4e0e\u8005\u7b56\u7565\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/receiver"})
    public List<Map<String, Object>> getReceiverSource(@RequestBody MessageInstanceBaseContext context) {
        return this.messageInstanceService.getReceiverSource(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e - \u6d88\u606f\u754c\u9762\u63a5\u6536\u4eba\u53c2\u4e0e\u8005\u7b56\u7565\u6821\u9a8c\u3001\u7b56\u7565\u964d\u7ea7")
    @PostMapping(value={"/receiver/downgrade"})
    public List<Map<String, Object>> receiverDowngrade(@RequestBody MessageInstanceStrategyVerifyContext context) {
        return this.messageInstanceService.receiverDowngrade(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e - \u53ef\u9009\u53d8\u91cf\u52a8\u6001\u5b57\u7b26\u4e32\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/variable"})
    public List<Map<String, Object>> getVariableSource(@RequestBody @Valid MessageInstanceBaseContext context) {
        return this.messageInstanceService.getVariableSource(context);
    }
}

