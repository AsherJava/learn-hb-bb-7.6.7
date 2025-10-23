/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;

public class ProcessStatusWithOperation
implements IProcessStatusWithOperation {
    private final IProcessStatus status;
    private final IProcessOperation operation;

    public ProcessStatusWithOperation(IProcessStatus status, IProcessOperation operation) {
        this.status = status;
        this.operation = operation;
    }

    @Override
    public String getCode() {
        return this.status.getCode();
    }

    @Override
    public String getTitle() {
        return this.status.getTitle();
    }

    @Override
    public String getAlias() {
        return this.status.getAlias();
    }

    @Override
    public IProcessStatus.DataAccessStatus getDataAccessStatus() {
        return this.status.getDataAccessStatus();
    }

    @Override
    public IProcessStatus.DataReportStatus getDataReportStatus() {
        return this.status.getDataReportStatus();
    }

    @Override
    public String getIcon() {
        return this.status.getIcon();
    }

    @Override
    public String getColor() {
        return this.status.getColor();
    }

    @Override
    public IProcessOperation getOperation() {
        return this.operation;
    }
}

