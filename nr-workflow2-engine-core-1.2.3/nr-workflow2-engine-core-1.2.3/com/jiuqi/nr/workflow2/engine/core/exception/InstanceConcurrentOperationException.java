/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;

public class InstanceConcurrentOperationException
extends ProcessRuntimeException {
    private static final long serialVersionUID = 3913493550037785821L;
    private final String instanceId;

    public String getInstanceId() {
        return this.instanceId;
    }

    public InstanceConcurrentOperationException(String processEngineId, String instanceId) {
        super(processEngineId, ErrorCode.CONCURRENT_OPERATION, "instance " + instanceId + " not found.");
        this.instanceId = instanceId;
    }
}

