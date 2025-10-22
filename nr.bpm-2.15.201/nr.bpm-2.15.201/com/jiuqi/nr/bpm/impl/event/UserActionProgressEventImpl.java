/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchEventImpl;

public class UserActionProgressEventImpl
extends UserActionBatchEventImpl
implements UserActionProgressEvent {
    private long totalSteps;
    private long completedSteps;

    @Override
    public long getTotalSteps() {
        return this.totalSteps;
    }

    public void setTotalSteps(long totalSteps) {
        this.totalSteps = totalSteps;
    }

    @Override
    public long getCompletedSteps() {
        return this.completedSteps;
    }

    public void setCompletedSteps(long completedSteps) {
        this.completedSteps = completedSteps;
    }

    public void doStep() {
        ++this.completedSteps;
    }
}

