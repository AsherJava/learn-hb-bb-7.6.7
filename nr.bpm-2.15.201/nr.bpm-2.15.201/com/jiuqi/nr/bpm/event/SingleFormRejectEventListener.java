/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectPrepareEventImpl;
import java.util.Set;

public interface SingleFormRejectEventListener {
    public Integer getSequence();

    default public Set<String> getListeningActionId() {
        return null;
    }

    public void onPrepare(SingleFormRejectPrepareEventImpl var1) throws Exception;

    public void onComplete(SingleFormRejectCompleteEventImpl var1) throws Exception;
}

