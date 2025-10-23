/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.envimpl;

import com.jiuqi.nr.workflow2.todo.env.TodoFilterConditionContext;

public class TodoFilterConditionContextImpl
implements TodoFilterConditionContext {
    private String taskId;
    private String entityCaliber;
    private String period;
    private String flowNodeCode;

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getEntityCaliber() {
        return this.entityCaliber;
    }

    public void setEntityCaliber(String entityCaliber) {
        this.entityCaliber = entityCaliber;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getFlowNodeCode() {
        return this.flowNodeCode;
    }

    public void setFlowNodeCode(String flowNodeCode) {
        this.flowNodeCode = flowNodeCode;
    }
}

