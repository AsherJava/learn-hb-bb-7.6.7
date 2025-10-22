/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.delegate.DelegateTask
 *  org.activiti.engine.delegate.TaskListener
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.event.ActivitiTaskCreateEvent;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import java.util.Optional;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="ActivitiTaskCreateListenerBean")
public class ActivitiTaskCreateListener
implements TaskListener {
    private static final long serialVersionUID = 9086331842937838641L;
    @Autowired
    private Optional<EventDispatcher> actionEventHandler;

    public void notify(DelegateTask delegateTask) {
        switch (delegateTask.getEventName()) {
            case "create": {
                ActivitiTaskCreateEvent event = new ActivitiTaskCreateEvent(delegateTask);
                try {
                    this.actionEventHandler.get().onCreateTask(event);
                    break;
                }
                catch (Exception e) {
                    throw new BpmException(e);
                }
            }
        }
    }
}

