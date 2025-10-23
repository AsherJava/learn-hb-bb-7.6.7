/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;

public class UserTaskNotFoundException
extends ProcessDefinitionException {
    private static final long serialVersionUID = 7426623678461358366L;

    public UserTaskNotFoundException(String processEngineId, String processId, String userTaskCode) {
        super(processEngineId, processId, ErrorCode.USERACTION_NOT_FOUND, "user task " + userTaskCode + " not found in process " + processId + ".");
    }
}

