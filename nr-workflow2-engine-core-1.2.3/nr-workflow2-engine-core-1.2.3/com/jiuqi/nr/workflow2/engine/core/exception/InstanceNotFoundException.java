/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;

public class InstanceNotFoundException
extends ProcessRuntimeException {
    private static final long serialVersionUID = 3913493550037785821L;
    private final IBusinessKey businessKey;
    private final String instanceId;

    public IBusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public InstanceNotFoundException(String processEngineId, IBusinessKey businessKey) {
        super(processEngineId, ErrorCode.INSTANCE_NOT_FOUND, "instance " + businessKey + " not found.");
        this.businessKey = businessKey;
        this.instanceId = null;
    }

    public InstanceNotFoundException(String processEngineId, String instanceId) {
        super(processEngineId, ErrorCode.INSTANCE_NOT_FOUND, "instance " + instanceId + " not found.");
        this.businessKey = null;
        this.instanceId = instanceId;
    }
}

