/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class TaskSuspendedError
extends BpmException {
    private static final long serialVersionUID = -8676509710994485855L;

    public TaskSuspendedError(String taskId) {
        super(String.format("process task %s is suspended.", taskId));
    }
}

