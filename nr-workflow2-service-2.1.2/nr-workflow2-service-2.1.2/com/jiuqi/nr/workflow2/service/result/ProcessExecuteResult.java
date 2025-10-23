/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 */
package com.jiuqi.nr.workflow2.service.result;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;

public class ProcessExecuteResult
implements IProcessExecuteResult {
    private String executeMessage;
    private ProcessExecuteStatus executeStatus;
    private IUserAction userAction;

    public ProcessExecuteResult(ProcessExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    public ProcessExecuteResult(ProcessExecuteStatus executeStatus, IUserAction userAction) {
        this(executeStatus);
        this.userAction = userAction;
    }

    public ProcessExecuteResult(ProcessExecuteStatus executeStatus, String executeMessage) {
        this(executeStatus);
        this.executeMessage = executeMessage;
    }

    public ProcessExecuteResult(ProcessExecuteStatus executeStatus, String executeMessage, IUserAction userAction) {
        this(executeStatus, executeMessage);
        this.userAction = userAction;
    }

    @Override
    public String getExecuteMessage() {
        return this.executeMessage;
    }

    public void setExecuteMessage(String executeMessage) {
        this.executeMessage = executeMessage;
    }

    @Override
    public ProcessExecuteStatus getExecuteStatus() {
        return this.executeStatus;
    }

    public void setExecuteStatus(ProcessExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    @Override
    public IUserAction getUserAction() {
        return this.userAction;
    }

    public void setUserAction(IUserAction userAction) {
        this.userAction = userAction;
    }
}

