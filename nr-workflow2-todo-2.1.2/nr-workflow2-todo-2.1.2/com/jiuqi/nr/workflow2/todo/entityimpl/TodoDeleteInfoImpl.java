/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

import com.jiuqi.nr.workflow2.todo.entity.TodoDeleteInfo;

public class TodoDeleteInfoImpl
implements TodoDeleteInfo {
    private String workflowNodeTask;
    private String workflowInstance;

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

