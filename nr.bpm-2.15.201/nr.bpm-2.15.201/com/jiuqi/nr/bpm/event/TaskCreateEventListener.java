/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import com.jiuqi.nr.definition.internal.impl.FlowsType;

public interface TaskCreateEventListener {
    public void onCreate(TaskCreateEvent var1);

    public void onBatchCreate(TaskCreateEvent var1);

    public FlowsType getFlowsType();
}

