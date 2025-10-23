/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;

public class WorkflowSettingsDTO {
    private String taskId;
    private String workflowEngine;
    private String workflowDefine;
    private boolean workflowEnable;
    private boolean todoEnable;
    private WorkflowObjectType workflowObjectType;
    private String otherConfig;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getWorkflowEngine() {
        return this.workflowEngine;
    }

    public void setWorkflowEngine(String workflowEngine) {
        this.workflowEngine = workflowEngine;
    }

    public String getWorkflowDefine() {
        return this.workflowDefine;
    }

    public void setWorkflowDefine(String workflowDefine) {
        this.workflowDefine = workflowDefine;
    }

    public boolean isWorkflowEnable() {
        return this.workflowEnable;
    }

    public void setWorkflowEnable(boolean workflowEnable) {
        this.workflowEnable = workflowEnable;
    }

    public boolean isTodoEnable() {
        return this.todoEnable;
    }

    public void setTodoEnable(boolean todoEnable) {
        this.todoEnable = todoEnable;
    }

    public WorkflowObjectType getWorkflowObjectType() {
        return this.workflowObjectType;
    }

    public void setWorkflowObjectType(WorkflowObjectType workflowObjectType) {
        this.workflowObjectType = workflowObjectType;
    }

    public String getOtherConfig() {
        return this.otherConfig;
    }

    public void setOtherConfig(String otherConfig) {
        this.otherConfig = otherConfig;
    }
}

