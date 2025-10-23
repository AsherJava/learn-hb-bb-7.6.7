/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;

public class ProcessIOException
extends ProcessRuntimeException {
    private static final long serialVersionUID = 4149937907729663888L;

    public ProcessIOException(String processEngineId, String message) {
        super(processEngineId, ErrorCode.UNKNOW, message);
    }

    public ProcessIOException(String processEngineId, ErrorCode errorCode, String message) {
        super(processEngineId, errorCode, message);
    }

    public ProcessIOException(String processEngineId, String message, Throwable error) {
        super(processEngineId, message, error);
    }
}

