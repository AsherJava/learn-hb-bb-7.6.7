/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessInstanceStateError
extends BpmException {
    private static final long serialVersionUID = -8456972925293244308L;
    private ProcessInstance.ProcessInstanceState currentState;
    private ProcessInstance.ProcessInstanceState[] expectStates;

    public ProcessInstanceStateError(String processInstanceId, ProcessInstance.ProcessInstanceState currentState, ProcessInstance.ProcessInstanceState ... expectStates) {
        super(String.format("prcess task %s state error.", processInstanceId));
        this.currentState = currentState;
        this.expectStates = expectStates;
    }

    public ProcessInstanceStateError() {
        super(String.format("prcess state error.", new Object[0]));
    }

    public ProcessInstance.ProcessInstanceState getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(ProcessInstance.ProcessInstanceState currentState) {
        this.currentState = currentState;
    }

    public ProcessInstance.ProcessInstanceState[] getExpectStates() {
        return this.expectStates;
    }

    public void setExpectStates(ProcessInstance.ProcessInstanceState[] expectStates) {
        this.expectStates = expectStates;
    }
}

