/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.activiti.engine.delegate.DelegateExecution
 *  org.activiti.engine.delegate.ExecutionListener
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.event.ActivitiExecutionEndEvent;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.definition.internal.BeanUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="ActivtiExecutionListenerBean")
public class ActivitiExecutionListener
implements ExecutionListener {
    private static final long serialVersionUID = 9086331842937838641L;
    @Autowired
    private EventDispatcher actionEventHandler;

    public void notify(DelegateExecution execution) {
        ActivitiExecutionEndEvent event;
        if (this.actionEventHandler == null) {
            this.actionEventHandler = (EventDispatcher)BeanUtil.getBean(EventDispatcher.class);
        }
        if ("end".equals(execution.getEventName())) {
            event = new ActivitiExecutionEndEvent(execution);
            try {
                this.actionEventHandler.onEnd(event);
            }
            catch (Exception e) {
                throw new BpmException(e);
            }
        }
        if ("start".equals(execution.getEventName())) {
            event = new ActivitiExecutionEndEvent(execution);
            try {
                this.actionEventHandler.onStart(event);
            }
            catch (Exception e) {
                throw new BpmException(e);
            }
        }
    }
}

