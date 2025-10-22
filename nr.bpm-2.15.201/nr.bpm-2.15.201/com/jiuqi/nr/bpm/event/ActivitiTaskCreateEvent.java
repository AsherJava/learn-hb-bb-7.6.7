/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.delegate.DelegateTask
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import org.activiti.engine.delegate.DelegateTask;

public class ActivitiTaskCreateEvent
extends TaskCreateEvent {
    private DelegateTask delegateTask;

    public ActivitiTaskCreateEvent(DelegateTask delegateTask) {
        this.delegateTask = delegateTask;
    }

    public DelegateTask getDelegateTask() {
        return this.delegateTask;
    }

    public void setDelegateTask(DelegateTask delegateTask) {
        this.delegateTask = delegateTask;
    }
}

