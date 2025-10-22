/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessDefinitionError
extends BpmException {
    private static final long serialVersionUID = 7299443453796658003L;

    public ProcessDefinitionError(String processId, Exception e) {
        super(String.format("pre defined process %s error.", processId), e);
    }

    public ProcessDefinitionError(String processId) {
        super(String.format("pre defined process %s error.", processId));
    }
}

