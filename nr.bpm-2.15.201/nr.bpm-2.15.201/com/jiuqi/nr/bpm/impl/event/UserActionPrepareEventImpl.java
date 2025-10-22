/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.impl.event.UserActionEventImpl;

public class UserActionPrepareEventImpl
extends UserActionEventImpl
implements UserActionPrepareEvent {
    private boolean isSetBreak;
    private String breakMessage;

    @Override
    public void setBreak(String message) {
        this.isSetBreak = true;
        this.breakMessage = message;
    }

    @Override
    public void replaceComment(String newCommnet) {
        this.setComment(newCommnet);
    }

    public boolean isSetBreak() {
        return this.isSetBreak;
    }

    public String getBreakMessage() {
        return this.breakMessage;
    }
}

