/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import java.util.Calendar;

public class ProcessIOOperation
implements IProcessIOOperation {
    private String instanceId;
    private String sourceUserTask;
    private String targetUserTask;
    private String action;
    private Calendar operateTime;
    private String operator;
    private String comment;
    private boolean forceReport;

    public ProcessIOOperation() {
    }

    public ProcessIOOperation(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String getSourceUserTask() {
        return this.sourceUserTask;
    }

    public void setSourceUserTask(String sourceUserTask) {
        this.sourceUserTask = sourceUserTask;
    }

    @Override
    public String getTargetUserTask() {
        return this.targetUserTask;
    }

    public void setTargetUserTask(String targetUserTask) {
        this.targetUserTask = targetUserTask;
    }

    @Override
    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public Calendar getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Calendar operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean isForceReport() {
        return this.forceReport;
    }

    public void setForceReport(boolean forceReport) {
        this.forceReport = forceReport;
    }
}

