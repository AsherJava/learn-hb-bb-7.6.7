/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.ActivitiExecutionEndEvent;
import com.jiuqi.nr.bpm.event.ActivitiExecutionEventListener;
import com.jiuqi.nr.bpm.event.ISingleFormRejectEvent;
import com.jiuqi.nr.bpm.event.ReferCacheEvent;
import com.jiuqi.nr.bpm.event.SingleFormRejectEventListener;
import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import com.jiuqi.nr.bpm.event.TaskCreateEventListener;
import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchEvent;
import com.jiuqi.nr.bpm.event.UserActionEvent;
import com.jiuqi.nr.bpm.event.UserActionEventListener;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import com.jiuqi.nr.bpm.impl.common.BpmCacheMessageSender;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchPrepareEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionPrepareEventImpl;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectPrepareEventImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class EventDispatcher
implements InitializingBean {
    @Autowired(required=false)
    private List<UserActionEventListener> userActionListeners;
    @Autowired(required=false)
    private List<TaskCreateEventListener> createTaskListeners;
    @Autowired(required=false)
    private List<ActivitiExecutionEventListener> executionListeners;
    @Autowired
    BpmCacheMessageSender bpmCacheMessageSender;
    @Autowired(required=false)
    private List<SingleFormRejectEventListener> sigleFormRejectListeners;

    public void onCreateTask(TaskCreateEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (TaskCreateEventListener listener : this.matchListenner(event)) {
            listener.onCreate(event);
        }
    }

    public void onBatchCreateTask(TaskCreateEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (TaskCreateEventListener listener : this.matchListenner(event)) {
            listener.onBatchCreate(event);
        }
    }

    public void onEnd(ActivitiExecutionEndEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (ActivitiExecutionEventListener listener : this.executionListeners) {
            listener.onEnd(event);
        }
    }

    public void onStart(ActivitiExecutionEndEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (ActivitiExecutionEventListener listener : this.executionListeners) {
            listener.onStart(event);
        }
    }

    public void onUserActionPrepare(UserActionPrepareEventImpl event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventListener listener : this.matchListenner(event)) {
            listener.onPrepare(event);
            if (!event.isSetBreak()) continue;
            break;
        }
    }

    public void onUserActionComplete(UserActionCompleteEventImpl event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventListener listener : this.matchListenner(event)) {
            listener.onComplete(event);
        }
    }

    public void onUserActionBatchPrepare(UserActionBatchPrepareEventImpl event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventListener listener : this.matchListenner(event)) {
            listener.onBatchPrepare(event);
            if (!event.isSetBreak()) continue;
            break;
        }
    }

    public void onUserActionBatchComplete(UserActionBatchCompleteEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventListener listener : this.matchListenner(event)) {
            listener.onBatchComplete(event);
        }
    }

    public void onUserActionProgressChanged(UserActionProgressEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventListener listener : this.matchListenner(event)) {
            listener.onProgrocessChanged(event);
        }
    }

    public void onCacheChanged(ReferCacheEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        this.bpmCacheMessageSender.sendCacheClearMessage(event.getProcessDefinitionKey());
    }

    List<UserActionEventListener> matchListenner(UserActionEvent event) {
        return this.userActionListeners.stream().filter(o -> !(o.getListeningUserTaskId() != null && !o.getListeningUserTaskId().contains(event.getUserTaskId()) || o.getListeningActionId() != null && !o.getListeningActionId().contains(event.getActionId()))).collect(Collectors.toList());
    }

    List<UserActionEventListener> matchListenner(UserActionBatchEvent event) {
        return this.userActionListeners.stream().filter(o -> o.getListeningActionId() == null || o.getListeningActionId().contains(event.getActionId())).collect(Collectors.toList());
    }

    List<TaskCreateEventListener> matchListenner(TaskCreateEvent event) {
        return this.createTaskListeners.stream().filter(o -> o.getFlowsType().equals((Object)event.getFlowsType())).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.userActionListeners == null) {
            this.userActionListeners = new ArrayList<UserActionEventListener>();
        }
        this.userActionListeners.sort((o1, o2) -> o1.getSequence().compareTo(o2.getSequence()));
    }

    public void onSingleFormRejectPrepare(SingleFormRejectPrepareEventImpl event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (SingleFormRejectEventListener listener : this.matchListenner(event)) {
            listener.onPrepare(event);
            if (!event.isSetBreak()) continue;
            break;
        }
    }

    public void onSingleFormRejectComplete(SingleFormRejectCompleteEventImpl event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (SingleFormRejectEventListener listener : this.matchListenner(event)) {
            listener.onComplete(event);
        }
    }

    List<SingleFormRejectEventListener> matchListenner(ISingleFormRejectEvent event) {
        if (this.sigleFormRejectListeners == null) {
            return new ArrayList<SingleFormRejectEventListener>();
        }
        return this.sigleFormRejectListeners.stream().filter(o -> o.getListeningActionId() == null || o.getListeningActionId().contains(event.getActionId())).collect(Collectors.toList());
    }
}

