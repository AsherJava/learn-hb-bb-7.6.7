/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.common.TaskContext;

public interface UserActionBatchEvent {
    public String getActionId();

    public String getUserTaskId();

    public Actor getActor();

    public String getComment();

    public TaskContext getContext();

    public String getOperationId();

    public String getTaskId();

    public String getCorporateValue();
}

