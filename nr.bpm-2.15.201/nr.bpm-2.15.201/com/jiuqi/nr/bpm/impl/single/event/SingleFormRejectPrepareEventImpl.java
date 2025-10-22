/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.single.event;

import com.jiuqi.nr.bpm.event.SingleFormRejectPrepareEvent;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectEvent;

public class SingleFormRejectPrepareEventImpl
extends SingleFormRejectEvent
implements SingleFormRejectPrepareEvent {
    private boolean isSetBreak;
    private String breakMessage;

    @Override
    public void setBreak(String message) {
        this.isSetBreak = true;
        this.breakMessage = message;
    }

    public boolean isSetBreak() {
        return this.isSetBreak;
    }

    public String getBreakMessage() {
        return this.breakMessage;
    }
}

