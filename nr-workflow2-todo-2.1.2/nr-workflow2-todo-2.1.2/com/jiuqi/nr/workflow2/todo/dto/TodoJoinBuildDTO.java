/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.dto;

public class TodoJoinBuildDTO {
    private String taskId;
    private String entityCaliber;
    private String period;
    private String todoType;

    public TodoJoinBuildDTO(String taskId, String entityCaliber, String period, String todoType) {
        this.taskId = taskId;
        this.entityCaliber = entityCaliber;
        this.period = period;
        this.todoType = todoType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEntityCaliber() {
        return this.entityCaliber;
    }

    public void setEntityCaliber(String entityCaliber) {
        this.entityCaliber = entityCaliber;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTodoType() {
        return this.todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }
}

