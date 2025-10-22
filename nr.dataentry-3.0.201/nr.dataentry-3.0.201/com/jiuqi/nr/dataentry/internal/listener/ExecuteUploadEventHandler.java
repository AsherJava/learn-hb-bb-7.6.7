/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent
 *  com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent
 *  com.jiuqi.nr.bpm.event.UserActionCompleteEvent
 *  com.jiuqi.nr.bpm.event.UserActionEventListener
 *  com.jiuqi.nr.bpm.event.UserActionPrepareEvent
 *  com.jiuqi.nr.bpm.event.UserActionProgressEvent
 */
package com.jiuqi.nr.dataentry.internal.listener;

import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionEventListener;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ExecuteUploadEventHandler
implements UserActionEventListener {
    public Integer getSequence() {
        return 0;
    }

    public void onPrepare(UserActionPrepareEvent event) throws Exception {
    }

    public void onComplete(UserActionCompleteEvent event) throws Exception {
    }

    public void onBatchPrepare(UserActionBatchPrepareEvent event) throws Exception {
    }

    public void onProgrocessChanged(UserActionProgressEvent event) throws Exception {
    }

    public void onBatchComplete(UserActionBatchCompleteEvent event) throws Exception {
    }

    public Set<String> getListeningActionId() {
        HashSet<String> LISTENINGACTIONS = new HashSet<String>();
        LISTENINGACTIONS.add("act_upload");
        return LISTENINGACTIONS;
    }
}

