/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.event;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public class TodoDeleteEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -1408778787406177695L;
    private final List<String> processInstanceIds;

    public TodoDeleteEvent(Object source, List<String> processInstanceIds) {
        super(source);
        this.processInstanceIds = processInstanceIds;
    }

    public List<String> getProcessInstanceIds() {
        return this.processInstanceIds;
    }
}

