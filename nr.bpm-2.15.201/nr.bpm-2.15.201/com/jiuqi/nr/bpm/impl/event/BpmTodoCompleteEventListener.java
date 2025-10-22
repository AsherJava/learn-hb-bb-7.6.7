/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionEventListener;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BpmTodoCompleteEventListener
implements UserActionEventListener {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onComplete(UserActionCompleteEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        ArrayList<String> userId = new ArrayList<String>();
        userId.add(event.getActorId());
    }

    @Override
    public Integer getSequence() {
        return 1;
    }

    @Override
    public void onPrepare(UserActionPrepareEvent event) throws Exception {
    }

    @Override
    public void onBatchPrepare(UserActionBatchPrepareEvent event) throws Exception {
    }

    @Override
    public void onProgrocessChanged(UserActionProgressEvent event) throws Exception {
    }

    @Override
    public void onBatchComplete(UserActionBatchCompleteEvent event) throws Exception {
    }
}

