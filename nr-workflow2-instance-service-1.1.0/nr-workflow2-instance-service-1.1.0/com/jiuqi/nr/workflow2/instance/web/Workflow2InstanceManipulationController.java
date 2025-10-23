/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.workflow2.instance.web;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.instance.context.InstanceBaseContext;
import com.jiuqi.nr.workflow2.instance.context.InstanceOperateContext;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceManipulationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/nr/workflow2/instance"})
@Api(tags={"\u6d41\u7a0b2.0-\u6d41\u7a0b\u5b9e\u4f8b\u670d\u52a1"})
public class Workflow2InstanceManipulationController {
    @Resource
    private Workflow2InstanceManipulationService service;

    @NRContextBuild
    @PostMapping(value={"/operate-instance"})
    @ApiOperation(value="\u64cd\u4f5c\u6d41\u7a0b\u5b9e\u4f8b")
    public AsyncTaskInfo operateWorkflowInstance(@RequestBody InstanceOperateContext context) {
        return this.service.operateWorkflowInstance(context);
    }

    @NRContextBuild
    @PostMapping(value={"/refresh-participant"})
    @ApiOperation(value="\u5237\u65b0\u53c2\u4e0e\u8005")
    public AsyncTaskInfo refreshParticipant(@RequestBody InstanceBaseContext context) {
        return this.service.refreshParticipant(context);
    }
}

