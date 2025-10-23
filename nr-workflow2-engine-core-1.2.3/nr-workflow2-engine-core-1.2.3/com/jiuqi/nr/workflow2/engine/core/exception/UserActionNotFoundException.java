/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;

public class UserActionNotFoundException
extends ProcessDefinitionException {
    private static final long serialVersionUID = 263581907212243472L;

    public UserActionNotFoundException(String processEngineId, String processId, String userTaskCode, String actionCode) {
        super(processEngineId, processId, ErrorCode.USERACTION_NOT_FOUND, "action " + actionCode + " not found in process " + processId + " form task " + userTaskCode + ".");
    }
}

