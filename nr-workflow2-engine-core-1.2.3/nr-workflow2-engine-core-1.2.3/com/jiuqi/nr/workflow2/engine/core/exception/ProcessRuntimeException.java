/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessEngineException;

public class ProcessRuntimeException
extends ProcessEngineException {
    private static final long serialVersionUID = 7341034988921641551L;

    public ProcessRuntimeException(String processEngineId, String message) {
        this(processEngineId, ErrorCode.UNKNOW, message);
    }

    public ProcessRuntimeException(String processEngineId, ErrorCode errorCode, String message) {
        super(processEngineId, errorCode, message);
    }

    public ProcessRuntimeException(String processEngineId, String message, Throwable error) {
        super(processEngineId, message, error);
    }
}

