/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.exception.TaskNotFoundError;

public class TaskNotFoundException
extends ProcessRuntimeException {
    private static final long serialVersionUID = 3913493550037785821L;
    private final TaskNotFoundError.TaskNotFoundErrorData errorData;

    public TaskNotFoundError.TaskNotFoundErrorData getErrorData() {
        return this.errorData;
    }

    public TaskNotFoundException(String processEngineId, TaskNotFoundError.TaskNotFoundErrorData errorData) {
        super(processEngineId, ErrorCode.TASK_NOT_FOUND, "task " + errorData.getOrginalTaskId() + " not found.");
        this.errorData = errorData;
    }
}

