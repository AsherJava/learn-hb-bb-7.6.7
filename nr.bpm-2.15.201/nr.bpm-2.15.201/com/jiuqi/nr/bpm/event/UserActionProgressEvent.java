/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.UserActionBatchEvent;

public interface UserActionProgressEvent
extends UserActionBatchEvent {
    public long getTotalSteps();

    public long getCompletedSteps();
}

