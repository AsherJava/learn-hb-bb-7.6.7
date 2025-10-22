/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.definition.internal.impl.FlowsType;

public abstract class TaskCreateEvent {
    public FlowsType getFlowsType() {
        return FlowsType.WORKFLOW;
    }
}

