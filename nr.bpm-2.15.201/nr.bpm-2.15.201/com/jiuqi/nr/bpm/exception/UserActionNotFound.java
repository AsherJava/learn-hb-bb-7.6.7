/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class UserActionNotFound
extends BpmException {
    private static final long serialVersionUID = 7613702963799646603L;

    public UserActionNotFound(String actionId, String userTaskId) {
        super(String.format("can not find action %s in user task %s.", actionId, userTaskId));
    }

    public UserActionNotFound(String actionId, String userTaskId, String processDefinitionId) {
        super(String.format("can not find action %s in user task %s prcess %s.", actionId, userTaskId, processDefinitionId));
    }
}

