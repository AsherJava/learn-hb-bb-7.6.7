/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.exception;

import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;

public class TaskExsitsException
extends NpAsyncTaskExecption {
    private static final long serialVersionUID = 7268921976962919566L;
    private String taskId;

    public TaskExsitsException(String taskId, String message) {
        super(message);
        this.taskId = taskId;
    }

    public TaskExsitsException(String taskId, Throwable cause) {
        super(cause);
        this.taskId = taskId;
    }

    public TaskExsitsException(String taskId, String message, Throwable cause) {
        super(message, cause);
        this.taskId = taskId;
    }

    public String getTaskId() {
        return this.taskId;
    }
}

