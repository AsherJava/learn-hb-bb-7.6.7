/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.workflow2.settings.message.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.settings.message.domain.MessageSampleDO;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageSampleSaveAsContext;
import com.jiuqi.nr.workflow2.settings.message.service.MessageSampleService;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageSampleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping(value={"/nr/workflow2/settings/message-sample"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u6d88\u606f\u8bbe\u7f6e-\u6d88\u606f\u793a\u4f8b"})
public class MessageSampleController {
    @Autowired
    private MessageSampleService messageSampleService;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e -\u6d88\u606f\u793a\u4f8b\u67e5\u8be2")
    @GetMapping(value={""})
    public List<MessageSampleVO> queryMessageSample(@RequestParam(required=false) @Valid String type, @RequestParam(required=false) @Valid String actionCode) {
        return this.messageSampleService.queryMessageSample(type, actionCode);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e -\u6d88\u606f\u793a\u4f8b\u6570\u636e\u7b5b\u9009\u6761\u4ef6\u6570\u636e\u6e90\u67e5\u8be2")
    @GetMapping(value={"/filterSource"})
    public Map<String, Object> getFilterConditionSource(@RequestParam @Valid String taskId, @RequestParam @Valid String workflowNode) {
        return this.messageSampleService.getFilterConditionSource(taskId, workflowNode);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e -\u6d88\u606f\u793a\u4f8b\u6570\u636e\u53e6\u5b58\u4e3a")
    @PostMapping(value={"/save-as"})
    public Map<String, Object> saveMessageSampleAs(@RequestBody @Valid MessageSampleSaveAsContext context) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            boolean isSuccessful = this.messageSampleService.saveMessageSampleAs(context);
            result.put("code", isSuccessful ? "success" : "fail");
            result.put("message", isSuccessful ? "\u4fdd\u5b58\u6210\u529f" : "\u4fdd\u5b58\u5931\u8d25");
        }
        catch (Exception e) {
            LoggerFactory.getLogger(MessageSampleController.class).error(e.getMessage(), e);
            result.put("code", "fail");
            result.put("message", e.getClass().getSimpleName());
        }
        return result;
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e -\u6d88\u606f\u793a\u4f8b\u6570\u636e\u5220\u9664")
    @GetMapping(value={"/delete"})
    public Map<String, Object> deleteMessageSample(@RequestParam @Valid String id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            boolean isSuccessful = this.messageSampleService.deleteMessageSample(id);
            result.put("code", isSuccessful ? "success" : "fail");
            result.put("message", isSuccessful ? "\u5220\u9664\u6210\u529f" : "\u5220\u9664\u5931\u8d25");
        }
        catch (Exception e) {
            LoggerFactory.getLogger(MessageSampleController.class).error(e.getMessage(), e);
            result.put("code", "fail");
            result.put("message", e.getClass().getSimpleName());
        }
        return result;
    }

    @ResponseBody
    @ApiOperation(value="\u6d88\u606f\u8bbe\u7f6e -\u6d88\u606f\u793a\u4f8b\u5217\u8868\u540d\u79f0\u662f\u5426\u91cd\u590d\u6821\u9a8c")
    @GetMapping(value={"/isTitleDuplicated"})
    public boolean verifyTitle(@RequestParam @Valid String title) {
        MessageSampleDO messageSampleDO = this.messageSampleService.queryMessageSampleByTitle(title);
        return messageSampleDO != null;
    }
}

