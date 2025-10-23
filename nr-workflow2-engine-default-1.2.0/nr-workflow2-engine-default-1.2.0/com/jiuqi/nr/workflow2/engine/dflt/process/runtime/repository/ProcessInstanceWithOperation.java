/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;

public class ProcessInstanceWithOperation {
    private ProcessInstanceDO instance;
    private ProcessOperationDO operation;

    public ProcessInstanceDO getInstance() {
        return this.instance;
    }

    public void setInstance(ProcessInstanceDO instance) {
        this.instance = instance;
    }

    public ProcessOperationDO getOperation() {
        return this.operation;
    }

    public void setOperation(ProcessOperationDO operation) {
        this.operation = operation;
    }
}

