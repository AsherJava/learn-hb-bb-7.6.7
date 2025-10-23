/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.workflow2.settings.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsQueryContext;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping(value={"/nr/workflow2/settings"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u67e5\u8be2"})
public class WorkflowSettingsQueryController {
    @Resource
    private WorkflowSettingsQueryService workflowSettingsQueryService;

    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u57fa\u7840\u8bbe\u7f6e\u67e5\u8be2")
    @GetMapping(value={"/workflowSettings"})
    public Map<String, Object> getWorkflowSettings(@RequestParam @Valid String taskId) {
        return this.workflowSettingsQueryService.getWorkflowSettings(taskId);
    }

    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u5176\u4ed6\u8bbe\u7f6e\u67e5\u8be2")
    @GetMapping(value={"/otherSettings"})
    public Map<String, Object> getOtherSettings(@RequestParam @Valid String taskId) {
        return this.workflowSettingsQueryService.getOtherSettings(taskId);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u300c\u9ed8\u8ba4\u6d41\u7a0b\u300d\u7528\u6237\u914d\u7f6e\u53ca\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/config/default"})
    public Map<String, Object> getConfigWithSource(@RequestBody @Valid WorkflowSettingsQueryContext context) {
        return this.workflowSettingsQueryService.getConfigWithSource(context);
    }

    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u300c\u81ea\u5b9a\u4e49\u6d41\u7a0b\u300d\u6570\u636e\u6e90\u67e5\u8be2")
    @GetMapping(value={"/config/custom"})
    public Map<String, Object> getCustomConfigSource(@RequestParam @Valid String taskId) {
        return this.workflowSettingsQueryService.getCustomConfigSource(taskId);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u300c\u9ed8\u8ba4\u6d41\u7a0b\u300d\u8282\u70b9\u5c5e\u6027\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/nodeSource/property"})
    public Map<String, Object> getNodePropertySource(@RequestBody @Valid WorkflowSettingsQueryContext context) {
        return this.workflowSettingsQueryService.getNodePropertySource(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u300c\u9ed8\u8ba4\u6d41\u7a0b\u300d\u8282\u70b9\u52a8\u4f5c\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/nodeSource/action"})
    public List<Map<String, Object>> getNodeActionSource(@RequestBody @Valid WorkflowSettingsQueryContext context) {
        return this.workflowSettingsQueryService.getNodeActionSource(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u300c\u9ed8\u8ba4\u6d41\u7a0b\u300d\u8282\u70b9\u4e8b\u4ef6\u5217\u8868\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/nodeSource/event"})
    public List<Map<String, Object>> getNodeEventSource(@RequestBody @Valid WorkflowSettingsQueryContext context) {
        return this.workflowSettingsQueryService.getNodeEventSource(context);
    }

    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 - \u4e8b\u4ef6\u6570\u636e\u6e90\u67e5\u8be2")
    @GetMapping(value={"/nodeSource/eventSource"})
    public Map<String, Object> getEventSource(@RequestParam(value="taskId") @Valid String taskId, @RequestParam(value="eventId") @Valid String eventId) {
        return this.workflowSettingsQueryService.getEventSource(taskId, eventId);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 -\u300c\u9ed8\u8ba4\u6d41\u7a0b\u300d\u53c2\u4e0e\u8005\u6570\u636e\u6e90\u67e5\u8be2")
    @PostMapping(value={"/nodeSource/participant"})
    public List<Map<String, Object>> getNodeParticipantSource(@RequestBody @Valid WorkflowSettingsQueryContext context) {
        return this.workflowSettingsQueryService.getNodeParticipantSource(context);
    }

    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 - \u5224\u65ad\u5f53\u524d\u4efb\u52a1\u662f\u5426\u5b58\u5728\u5df2\u542f\u52a8\u7684\u6d41\u7a0b\u5b9e\u4f8b")
    @GetMapping(value={"/instance/exist"})
    public boolean isExistWorkflowInstance(@RequestParam @Valid String taskId) {
        return this.workflowSettingsQueryService.isExistWorkflowInstance(taskId);
    }

    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 - \u4fdd\u5b58\u63d0\u793a\u4fe1\u606f\u67e5\u8be2")
    @GetMapping(value={"/saveTips"})
    public Map<String, Object> getSaveTips(@RequestParam @Valid String taskId, @RequestParam @Valid String workflowType, @RequestParam @Valid String submitMode) {
        return this.workflowSettingsQueryService.getSaveTips(taskId, workflowType, submitMode);
    }
}

