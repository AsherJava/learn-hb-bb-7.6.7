/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.UserActionEvent;

public interface UserActionCompleteEvent
extends UserActionEvent {
    public String getBusinessKey();
}

