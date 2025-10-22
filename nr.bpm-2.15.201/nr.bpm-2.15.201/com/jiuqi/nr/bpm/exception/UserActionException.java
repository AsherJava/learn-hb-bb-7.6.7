/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class UserActionException
extends BpmException {
    private static final long serialVersionUID = 4606608948006937027L;
    private String actionId;

    public String getActionId() {
        return this.actionId;
    }

    public UserActionException(String actionId, String message) {
        super(message);
        this.actionId = actionId;
    }

    public UserActionException(String actionId, Exception exception) {
        super(exception);
        this.actionId = actionId;
    }
}

