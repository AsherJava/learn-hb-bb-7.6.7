/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

public class EventException
extends RuntimeException {
    private static final long serialVersionUID = 974667711030719071L;
    private final String eventDefinitionId;

    public String getEventDefinitionId() {
        return this.eventDefinitionId;
    }

    public EventException(String eventDefinitionId, String message) {
        super(message);
        this.eventDefinitionId = eventDefinitionId;
    }
}

