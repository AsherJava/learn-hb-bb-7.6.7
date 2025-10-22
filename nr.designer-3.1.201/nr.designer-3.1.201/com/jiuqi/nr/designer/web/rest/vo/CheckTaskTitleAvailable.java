/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class CheckTaskTitleAvailable {
    private String taskKey;
    private String taskTitle;

    public CheckTaskTitleAvailable() {
    }

    public CheckTaskTitleAvailable(String taskKey, String taskTitle) {
        this.taskKey = taskKey;
        this.taskTitle = taskTitle;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}

