/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.service.impl.WorkflowSettingExtend
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.converter;

import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.impl.WorkflowSettingExtend;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkflowSettingConverter
implements WorkflowSettingExtend {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private WorkflowSettingsService workflowSettingsService;
    @Value(value="${jiuqi.nr.task2.enable:false}")
    private boolean isTaskVersion2_0;

    public WorkflowSettingDefine getWorkflowDefine(String formSchemeKey, WorkflowSettingDefine workflowSetting) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (!this.isTaskVersion2_0 || formScheme == null) {
            return workflowSetting;
        }
        FlowsType flowsType = formScheme.getFlowsSetting().getFlowsType();
        if (flowsType != null && flowsType.equals((Object)FlowsType.DEFAULT)) {
            return workflowSetting;
        }
        String taskId = formScheme.getTaskKey();
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskId);
        if (workflowDefine == null || workflowDefine.isEmpty()) {
            return workflowSetting;
        }
        workflowSetting.setDataId(formSchemeKey);
        workflowSetting.setWorkflowId(workflowDefine);
        return workflowSetting;
    }
}

