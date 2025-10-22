/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;

public interface UserActionEventHandler {
    public Integer getSequence();

    public String getProcessDefinitionId();

    public String getUserTaskId();

    public String getActionId();

    public void onActionPrepare(UserActionPrepareEvent var1) throws Exception;

    public void onActionComplete(UserActionCompleteEvent var1) throws Exception;
}

