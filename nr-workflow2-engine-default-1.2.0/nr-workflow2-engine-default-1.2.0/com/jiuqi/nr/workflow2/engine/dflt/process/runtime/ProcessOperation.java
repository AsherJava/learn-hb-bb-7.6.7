/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import java.util.Calendar;

public class ProcessOperation
implements IProcessOperation {
    private final ProcessOperationDO operation;

    public ProcessOperation(ProcessOperationDO operation) {
        this.operation = operation;
    }

    public String getFromNode() {
        return this.operation.getFromNode();
    }

    public String getToNode() {
        return this.operation.getToNode();
    }

    public String getAction() {
        return this.operation.getAction();
    }

    public String getOperator() {
        return this.operation.getOperate_user();
    }

    public String getComment() {
        return this.operation.getComment();
    }

    public Calendar getOperateTime() {
        return this.operation.getOperateTime();
    }

    public String getId() {
        return this.operation.getId();
    }

    public String getInstanceId() {
        return this.operation.getInstanceId();
    }

    public String getOperateType() {
        return this.operation.getOperate_type();
    }

    public boolean isForceReport() {
        return this.operation.isForceReport();
    }
}

