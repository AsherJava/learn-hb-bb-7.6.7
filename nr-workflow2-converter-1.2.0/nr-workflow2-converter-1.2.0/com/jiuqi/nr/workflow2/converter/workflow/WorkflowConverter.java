/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.workflow2.converter.workflow;

import com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class WorkflowConverter
extends Workflow {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public boolean isDefaultWorkflow(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.isDefaultWorkflow(formSchemeKey);
        }
        String taskKey = this.runTimeViewController.getFormScheme(formSchemeKey).getTaskKey();
        boolean workflowEnable = this.workflowSettingsService.queryTaskWorkflowEnable(taskKey);
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
        return workflowEnable && workflowEngine.equals("jiuqi.nr.default");
    }
}

