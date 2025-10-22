/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessDefinitionNotFound
extends BpmException {
    private static final long serialVersionUID = 3629941816730520128L;

    public ProcessDefinitionNotFound(String processDefinitionId) {
        super(String.format("can not find process definition %s.", processDefinitionId));
    }
}

