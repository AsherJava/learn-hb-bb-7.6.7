/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import java.util.Set;

public interface UserActionEventListener {
    public Integer getSequence();

    default public Set<String> getListeningUserTaskId() {
        return null;
    }

    default public Set<String> getListeningActionId() {
        return null;
    }

    public void onPrepare(UserActionPrepareEvent var1) throws Exception;

    public void onComplete(UserActionCompleteEvent var1) throws Exception;

    public void onBatchPrepare(UserActionBatchPrepareEvent var1) throws Exception;

    public void onProgrocessChanged(UserActionProgressEvent var1) throws Exception;

    public void onBatchComplete(UserActionBatchCompleteEvent var1) throws Exception;
}

