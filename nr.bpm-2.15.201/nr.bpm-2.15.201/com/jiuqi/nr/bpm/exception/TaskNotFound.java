/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class TaskNotFound
extends BpmException {
    private static final long serialVersionUID = -5440410342613272621L;
    private String taskId;

    public TaskNotFound(String taskId) {
        super(String.format("can not find process task.", new Object[0]));
        this.taskId = taskId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

