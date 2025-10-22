/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.common.TaskContext;

public interface ISingleFormRejectEvent {
    public String getActionId();

    public String getActorId();

    public TaskContext getContext();
}

