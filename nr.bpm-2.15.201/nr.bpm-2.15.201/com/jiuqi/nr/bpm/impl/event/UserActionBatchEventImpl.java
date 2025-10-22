/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.event.UserActionBatchEvent;

class UserActionBatchEventImpl
implements UserActionBatchEvent {
    private String actionId;
    private BusinessKeySet businessKeySet;
    private Actor actor;
    private String comment;
    private TaskContext context;
    private String operationId;
    private String userTaskId;
    private String taskId;
    private String corporateValue;

    UserActionBatchEventImpl() {
    }

    @Override
    public String getActionId() {
        return this.actionId;
    }

    @Override
    public String getUserTaskId() {
        return this.userTaskId;
    }

    public void setUserTaskId(String userTaskId) {
        this.userTaskId = userTaskId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public BusinessKeySet getBusinessKeySet() {
        return this.businessKeySet;
    }

    public void setBusinessKeySet(BusinessKeySet businessKeySet) {
        this.businessKeySet = businessKeySet;
    }

    @Override
    public Actor getActor() {
        return this.actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
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

    public void setCorporateValue(String corporateValue) {
        this.corporateValue = corporateValue;
    }
}

