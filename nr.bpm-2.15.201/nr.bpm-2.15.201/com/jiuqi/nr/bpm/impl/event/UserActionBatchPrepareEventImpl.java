/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchEventImpl;

public class UserActionBatchPrepareEventImpl
extends UserActionBatchEventImpl
implements UserActionBatchPrepareEvent {
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

