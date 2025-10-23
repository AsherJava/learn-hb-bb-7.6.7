/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessEngineException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;

public class DuplicateBusinessKeyException
extends ProcessEngineException {
    private static final long serialVersionUID = -8117250209086564087L;
    private final IBusinessKey businessKey;

    public IBusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public DuplicateBusinessKeyException(String processEngineId, IBusinessKey businessKey) {
        super(processEngineId, ErrorCode.BUSINESSKEY_DUPLICATE, "process instance duplicate.");
        this.businessKey = businessKey;
    }
}

