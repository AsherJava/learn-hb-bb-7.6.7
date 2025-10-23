/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 *  com.jiuqi.nr.bpm.de.dataflow.sendmsg.TodoSendFilter
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 */
package com.jiuqi.nr.workflow2.converter.todo;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.TodoSendFilter;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoSendFilterByWorkFlowSetting
implements TodoSendFilter {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IRunTimeViewController viewController;

    private static boolean taskIsMatchVersion2_0(TaskDefine task) {
        return "2.0".equals(task.getVersion());
    }

    public boolean isEnableSendTodo(BusinessKey businessKey) {
        FormSchemeDefine formScheme = this.viewController.getFormScheme(businessKey.getFormSchemeKey());
        TaskDefine taskDefine = this.viewController.getTask(formScheme.getTaskKey());
        if (TodoSendFilterByWorkFlowSetting.taskIsMatchVersion2_0(taskDefine)) {
            return this.workflowSettingsService.queryTaskTodoEnable(taskDefine.getKey());
        }
        return true;
    }
}

