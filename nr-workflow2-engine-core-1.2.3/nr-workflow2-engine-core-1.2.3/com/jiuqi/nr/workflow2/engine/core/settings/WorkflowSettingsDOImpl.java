/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;

public class WorkflowSettingsDOImpl
implements WorkflowSettingsDO {
    private String id;
    private String taskId;
    private String workflowEngine;
    private String workflowDefine;
    private boolean workflowEnable;
    private boolean todoEnable;
    private WorkflowObjectType workflowObjectType;
    private String otherConfig;
    private String createTime;
    private String updateTime;
    private String operator;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getWorkflowEngine() {
        return this.workflowEngine;
    }

    public void setWorkflowEngine(String workflowEngine) {
        this.workflowEngine = workflowEngine;
    }

    @Override
    public String getWorkflowDefine() {
        return this.workflowDefine;
    }

    public void setWorkflowDefine(String workflowDefine) {
        this.workflowDefine = workflowDefine;
    }

    @Override
    public boolean isWorkflowEnable() {
        return this.workflowEnable;
    }

    public void setWorkflowEnable(boolean workflowEnable) {
        this.workflowEnable = workflowEnable;
    }

    @Override
    public boolean isTodoEnable() {
        return this.todoEnable;
    }

    public void setTodoEnable(boolean todoEnable) {
        this.todoEnable = todoEnable;
    }

    @Override
    public WorkflowObjectType getWorkflowObjectType() {
        return this.workflowObjectType;
    }

    public void setWorkflowObjectType(WorkflowObjectType workflowObjectType) {
        this.workflowObjectType = workflowObjectType;
    }

    @Override
    public String getOtherConfig() {
        return this.otherConfig;
    }

    public void setOtherConfig(String otherConfig) {
        this.otherConfig = otherConfig;
    }

    @Override
    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

