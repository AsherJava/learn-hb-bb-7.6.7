/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.workflow2.settings.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.settings.async.WorkflowConfigSaveAsyncTask;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsManipulationContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping(value={"/nr/workflow2/settings"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u64cd\u4f5c"})
public class WorkflowSettingsManipulationController {
    @Resource
    private AsyncTaskManager asyncTaskManager;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212-\u4fdd\u5b58\u8bbe\u7f6e")
    @PostMapping(value={"/saveConfig"})
    public Map<String, Object> saveConfig(@RequestBody @Valid WorkflowSettingsManipulationContext context) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)context));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new WorkflowConfigSaveAsyncTask());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("asyncTaskId", asyncTaskId);
        return result;
    }
}

