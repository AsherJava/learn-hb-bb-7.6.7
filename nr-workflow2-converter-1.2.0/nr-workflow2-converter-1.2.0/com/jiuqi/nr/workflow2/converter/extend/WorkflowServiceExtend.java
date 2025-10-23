/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.impl.event.WorkflowDeleteEventImpl
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.settings.extend.WorkflowSettingsExtend
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.converter.extend;

import com.jiuqi.nr.bpm.impl.event.WorkflowDeleteEventImpl;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.settings.extend.WorkflowSettingsExtend;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceExtend
implements WorkflowSettingsExtend {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private WorkflowDeleteEventImpl workflowDeleteEvent;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;

    public void clearWorkflowInstanceByTask(String taskId, WorkflowObjectType workflowObjectType) {
        List formSchemeDefines = this.runTimeViewController.listFormSchemeByTask(taskId);
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            this.batchQueryUploadStateService.deleteStateData(formSchemeDefine);
            this.batchQueryUploadStateService.deleteHistoryStateData(formSchemeDefine);
        }
    }
}

