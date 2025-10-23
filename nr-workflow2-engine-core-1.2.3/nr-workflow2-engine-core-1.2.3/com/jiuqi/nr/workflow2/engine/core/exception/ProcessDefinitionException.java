/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessEngineException;

public class ProcessDefinitionException
extends ProcessEngineException {
    private static final long serialVersionUID = -5860514778292147915L;
    private final String processId;

    public String getProcessId() {
        return this.processId;
    }

    public ProcessDefinitionException(String processEngineId, String processId, String message) {
        this(processEngineId, processId, ErrorCode.UNKNOW, message);
    }

    public ProcessDefinitionException(String processEngineId, String processId, ErrorCode errorCode, String message) {
        super(processEngineId, errorCode, message);
        this.processId = processId;
    }

    public ProcessDefinitionException(String processEngineId, String processId, Throwable error) {
        super(processEngineId, "process definiton error. process id: " + processId + ", process engine: " + processEngineId, error);
        this.processId = processId;
    }
}

