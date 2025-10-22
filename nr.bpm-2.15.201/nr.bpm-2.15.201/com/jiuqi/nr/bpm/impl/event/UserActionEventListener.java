/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionEventHandler;
import com.jiuqi.nr.bpm.impl.event.UserActionPrepareEventImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserActionEventListener {
    @Autowired(required=false)
    private List<UserActionEventHandler> handlers;

    public void onUserActionPrepare(UserActionPrepareEventImpl event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventHandler handler : this.matchHandler(event.getProcessDefinitionId(), event.getUserTaskId(), event.getActionId())) {
            handler.onActionPrepare(event);
            if (!event.isSetBreak()) continue;
            break;
        }
    }

    public void onUserActionComplete(UserActionCompleteEvent event) throws Exception {
        Assert.notNull((Object)event, "parameter event can not be null.");
        for (UserActionEventHandler handler : this.matchHandler(event.getProcessDefinitionId(), event.getUserTaskId(), event.getActionId())) {
            handler.onActionComplete(event);
        }
    }

    List<UserActionEventHandler> matchHandler(String processDefinitionId, String userTaskId, String actionId) {
        this.init();
        return this.handlers.stream().filter(o -> !(o.getProcessDefinitionId() != null && processDefinitionId != null && !o.getProcessDefinitionId().equals(processDefinitionId) || o.getUserTaskId() != null && userTaskId != null && !o.getUserTaskId().equals(userTaskId) || o.getActionId() != null && actionId != null && !o.getActionId().equals(actionId))).collect(Collectors.toList());
    }

    private void init() {
        if (this.handlers == null) {
            this.handlers = new ArrayList<UserActionEventHandler>();
        }
        this.handlers.sort((o1, o2) -> o1.getSequence().compareTo(o2.getSequence()));
    }
}

