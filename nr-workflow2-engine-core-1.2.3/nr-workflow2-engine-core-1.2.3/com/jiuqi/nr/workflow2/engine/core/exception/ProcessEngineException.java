/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;

public abstract class ProcessEngineException
extends RuntimeException {
    private static final long serialVersionUID = 3102254086505765123L;
    private final String processEngineId;
    private final ErrorCode errorCode;

    public String getProcessEngineId() {
        return this.processEngineId;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public ProcessEngineException(String processEngineId, ErrorCode errorCode, String message) {
        super(message);
        this.processEngineId = processEngineId;
        this.errorCode = errorCode;
    }

    public ProcessEngineException(String processEngineId, String message, Throwable error) {
        super(message, error);
        this.processEngineId = processEngineId;
        this.errorCode = ErrorCode.UNKNOW;
    }
}

