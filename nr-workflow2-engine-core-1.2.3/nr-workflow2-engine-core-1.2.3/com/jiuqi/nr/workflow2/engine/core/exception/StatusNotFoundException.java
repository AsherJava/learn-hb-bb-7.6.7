/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;

public class StatusNotFoundException
extends ProcessRuntimeException {
    private static final long serialVersionUID = 3913493550037785821L;
    private final String status;

    public String getStatus() {
        return this.status;
    }

    public StatusNotFoundException(String processEngineId, String status) {
        super(processEngineId, ErrorCode.STATUS_NOT_FOUND, "status " + status + " not found.");
        this.status = status;
    }
}

