/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.service.IProcessInstanceService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IProcessServiceHelper {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Resource(name="com.jiuqi.nr.workflow2.service.impl.ProcessInstanceService")
    private IProcessInstanceService defInstanceService;
    @Resource(name="com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectInstancesService")
    private IProcessInstanceService fromRejectInstanceService;

    public IProcessInstanceService getProcessInstanceService(String taskKey) {
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(taskKey);
        if (WorkflowObjectType.MD_WITH_SFR == workflowObjectType) {
            return this.fromRejectInstanceService;
        }
        return this.defInstanceService;
    }
}

