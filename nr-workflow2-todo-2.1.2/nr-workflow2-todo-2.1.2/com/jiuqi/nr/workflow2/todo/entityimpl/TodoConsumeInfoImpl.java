/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;

public class TodoConsumeInfoImpl
implements TodoConsumeInfo {
    private String businessKey;
    private String workflowNodeTask;
    private String workflowInstance;

    @Override
    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String getWorkflowNodeTask() {
        return this.workflowNodeTask;
    }

    public void setWorkflowNodeTask(String workflowNodeTask) {
        this.workflowNodeTask = workflowNodeTask;
    }

    @Override
    public String getWorkflowInstance() {
        return this.workflowInstance;
    }

    public void setWorkflowInstance(String workflowInstance) {
        this.workflowInstance = workflowInstance;
    }
}

