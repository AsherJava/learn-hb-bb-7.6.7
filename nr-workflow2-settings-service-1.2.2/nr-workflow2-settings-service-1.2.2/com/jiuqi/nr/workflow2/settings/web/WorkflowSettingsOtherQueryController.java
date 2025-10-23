/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.workflow2.settings.web;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.settings.async.enumeration.SaveConfigState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping(value={"/nr/workflow2/settings"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u5176\u4ed6\u67e5\u8be2"})
public class WorkflowSettingsOtherQueryController {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private AsyncTaskManager asyncTaskManager;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212-\u62a5\u8868\u65b9\u6848\u67e5\u8be2")
    @GetMapping(value={"/formScheme"})
    public Map<String, Object> getFormScheme(@RequestParam @Valid String taskId) {
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskId);
            Optional<FormSchemeDefine> firstFormSchemeDefine = formSchemeDefines.stream().max(Comparator.comparing(FormSchemeDefine::getFromPeriod));
            HashMap<String, Object> result = new HashMap<String, Object>();
            if (firstFormSchemeDefine.isPresent()) {
                FormSchemeDefine formSchemeDefine = firstFormSchemeDefine.get();
                String formSchemeKey = formSchemeDefine.getKey();
                String entityId = formSchemeDefine.getDw();
                result.put("formSchemeKey", formSchemeKey);
                result.put("entityId", entityId);
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212-\u4fdd\u5b58\u8fdb\u5ea6\u67e5\u8be2")
    @GetMapping(value={"/saveProgress"})
    public Map<String, Object> getSaveProgress(@RequestParam @Valid String asyncTaskId) {
        SaveConfigState state;
        AsyncTask asyncTask = this.asyncTaskManager.queryTask(asyncTaskId);
        int progress = (int)(asyncTask.getProcess() * 100.0);
        TaskState taskState = asyncTask.getState();
        String info = asyncTask.getResult();
        switch (taskState) {
            case WAITING: {
                state = SaveConfigState.ACTIVE;
                break;
            }
            case PROCESSING: {
                state = SaveConfigState.ACTIVE;
                break;
            }
            case FINISHED: {
                state = SaveConfigState.SUCCESS;
                break;
            }
            default: {
                state = SaveConfigState.ERROR;
            }
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("progress", progress);
        result.put("state", (Object)state);
        result.put("info", info);
        return result;
    }
}

