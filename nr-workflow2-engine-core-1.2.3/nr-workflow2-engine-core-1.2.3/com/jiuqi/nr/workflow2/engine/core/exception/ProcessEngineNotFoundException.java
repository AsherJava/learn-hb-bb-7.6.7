/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessEngineException;

public class ProcessEngineNotFoundException
extends ProcessEngineException {
    private static final long serialVersionUID = -8937782017893954875L;

    public ProcessEngineNotFoundException(String processEngineId) {
        super(processEngineId, ErrorCode.PROCESSENGINE_NOT_FOUND, "process engine " + processEngineId + " not found.");
    }
}

