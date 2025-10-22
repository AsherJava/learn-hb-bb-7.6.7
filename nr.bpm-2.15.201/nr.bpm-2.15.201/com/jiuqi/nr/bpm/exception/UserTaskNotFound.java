/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class UserTaskNotFound
extends BpmException {
    private static final long serialVersionUID = -4881646291883396258L;

    public UserTaskNotFound(String userTaskId) {
        super(String.format("can not find user task %s.", userTaskId));
    }

    public UserTaskNotFound(String userTaskId, String processDefinitionId) {
        super(String.format("can not find user task %s in process %s.", userTaskId, processDefinitionId));
    }
}

