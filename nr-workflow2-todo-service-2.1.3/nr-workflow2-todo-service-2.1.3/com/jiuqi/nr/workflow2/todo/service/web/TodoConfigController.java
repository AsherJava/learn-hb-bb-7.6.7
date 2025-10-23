/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.workflow2.todo.service.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"nr/workflow2/todo-config"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u5f85\u529e\u4e8b\u9879-\u914d\u7f6e\u670d\u52a1"})
public class TodoConfigController {
    @Resource
    private IRunTimeViewController rtViewService;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u53ef\u4ee5\u663e\u793a\u5f85\u529e\u7684\u4efb\u52a1")
    @GetMapping(value={"/inquire-todo-tasks"})
    public List<Map<String, String>> inquireTodoTasks() {
        ArrayList<Map<String, String>> todoTask = new ArrayList<Map<String, String>>();
        List allTaskDefines = this.rtViewService.getAllTaskDefines();
        allTaskDefines.forEach(task -> {
            if (this.isOpenWorkFlow((TaskDefine)task)) {
                HashMap<String, String> taskInfo = new HashMap<String, String>();
                taskInfo.put("key", task.getKey());
                taskInfo.put("code", task.getTaskCode());
                taskInfo.put("title", task.getTitle());
                todoTask.add(taskInfo);
            }
        });
        return todoTask;
    }

    private boolean isOpenWorkFlow(TaskDefine taskDefine) {
        boolean isOpen = false;
        if (null != taskDefine) {
            List<FormSchemeDefine> formSchemes = this.getFormSchemeByTask(taskDefine);
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
                if (flowsSetting == null) continue;
                FlowsType flowsType = flowsSetting.getFlowsType();
                switch (flowsType) {
                    case DEFAULT: 
                    case WORKFLOW: {
                        return true;
                    }
                }
            }
        }
        return isOpen;
    }

    private List<FormSchemeDefine> getFormSchemeByTask(TaskDefine taskDefine) {
        try {
            return this.rtViewService.queryFormSchemeByTask(taskDefine.getKey());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

