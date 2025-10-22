/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.UserActionEvent;

public interface UserActionPrepareEvent
extends UserActionEvent {
    public String getBusinessKey();

    public void setBreak(String var1);

    public void replaceComment(String var1);
}

