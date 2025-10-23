/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;

public class ProcessNotFoundException
extends ProcessDefinitionException {
    private static final long serialVersionUID = -8937782017893954875L;

    public ProcessNotFoundException(String processEngineId, String processId) {
        super(processEngineId, processId, ErrorCode.PROCESSDEFINITION_NOT_FOUND, "process " + processId + " not found in engine " + processEngineId + ".");
    }
}

