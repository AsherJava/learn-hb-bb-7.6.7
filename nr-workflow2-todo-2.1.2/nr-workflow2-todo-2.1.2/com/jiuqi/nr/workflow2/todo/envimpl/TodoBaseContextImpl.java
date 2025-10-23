/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.envimpl;

import com.jiuqi.nr.workflow2.todo.env.TodoBaseContext;

public class TodoBaseContextImpl
implements TodoBaseContext {
    private String taskId;
    private String entityCaliber;
    private String period;

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
}

