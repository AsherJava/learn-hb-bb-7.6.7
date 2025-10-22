/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.delegate.DelegateExecution
 */
package com.jiuqi.nr.bpm.event;

import org.activiti.engine.delegate.DelegateExecution;

public class ActivitiExecutionEndEvent {
    private DelegateExecution execution;

    public ActivitiExecutionEndEvent(DelegateExecution execution) {
        this.execution = execution;
    }

    public DelegateExecution getExecution() {
        return this.execution;
    }

    public void setExecution(DelegateExecution execution) {
        this.execution = execution;
    }
}

