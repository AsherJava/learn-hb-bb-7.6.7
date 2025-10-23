/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;

public class UnauthorizedException
extends ProcessRuntimeException {
    private static final long serialVersionUID = -5282430674710802793L;

    public UnauthorizedException(String processEngineId, String taskId, IActor actor, String action) {
        super(processEngineId, ErrorCode.TASK_NOT_FOUND, "Unauthorized Action! task:" + taskId + ", user:" + actor.getUserId() + ", action:" + action);
    }
}

