/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.common.TaskContext;

public interface UserActionEvent {
    public String getProcessDefinitionId();

    public String getUserTaskId();

    public String getActionId();

    public String getActorId();

    public String getComment();

    public TaskContext getContext();

    public String getOperationId();

    public String getTaskId();

    public String getCorporateValue();
}

