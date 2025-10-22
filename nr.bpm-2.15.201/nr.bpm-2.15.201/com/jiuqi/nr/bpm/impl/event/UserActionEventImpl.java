/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.event.UserActionEvent;

class UserActionEventImpl
implements UserActionEvent {
    private String processDefinitionId;
    private String userTaskId;
    private String actionId;
    private String actorId;
    private String businessKey;
    private String comment;
    private TaskContext context;
    private String operationId;
    private String taskId;
    private String corporateValue;

    UserActionEventImpl() {
    }

    @Override
    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public String getUserTaskId() {
        return this.userTaskId;
    }

    public void setUserTaskId(String userTaskId) {
        this.userTaskId = userTaskId;
    }

    @Override
    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String getActorId() {
        return this.actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public TaskContext getContext() {
        return this.context;
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

    @Override
    public String getOperationId() {
        return this.operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getCorporateValue() {
        return this.corporateValue;
    }

    public void setCorporateVaule(String corporateValue) {
        this.corporateValue = corporateValue;
    }
}

