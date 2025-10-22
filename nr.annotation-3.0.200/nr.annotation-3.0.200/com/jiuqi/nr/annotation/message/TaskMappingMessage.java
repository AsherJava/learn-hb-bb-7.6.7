/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import java.io.Serializable;

public class TaskMappingMessage
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String taskCode;
    private String taskTitle;

    public TaskMappingMessage() {
    }

    public TaskMappingMessage(String taskKey, String taskCode, String taskTitle) {
        this.taskKey = taskKey;
        this.taskCode = taskCode;
        this.taskTitle = taskTitle;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}

