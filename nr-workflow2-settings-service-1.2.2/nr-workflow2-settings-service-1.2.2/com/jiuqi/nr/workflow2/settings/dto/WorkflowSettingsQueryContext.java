/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.workflow2.settings.dto;

import com.jiuqi.nr.context.infc.impl.NRContext;

public class WorkflowSettingsQueryContext
extends NRContext {
    private String taskId;
    private String workflowNode;
    private String workflowDefineTemplate;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getWorkflowNode() {
        return this.workflowNode;
    }

    public void setWorkflowNode(String workflowNode) {
        this.workflowNode = workflowNode;
    }

    public String getWorkflowDefineTemplate() {
        return this.workflowDefineTemplate;
    }

    public void setWorkflowDefineTemplate(String workflowDefineTemplate) {
        this.workflowDefineTemplate = workflowDefineTemplate;
    }
}

