/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.event.UserActionBatchEvent;

public interface UserActionBatchPrepareEvent
extends UserActionBatchEvent {
    public BusinessKeySet getBusinessKeySet();

    public void setBreak(String var1);

    public void replaceComment(String var1);
}

