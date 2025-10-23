/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.EventException;

public class EventNotFoundException
extends EventException {
    private static final long serialVersionUID = 7289758626581839935L;

    public EventNotFoundException(String eventDefinitionId) {
        super(eventDefinitionId, "event " + eventDefinitionId + " not found.");
    }
}

