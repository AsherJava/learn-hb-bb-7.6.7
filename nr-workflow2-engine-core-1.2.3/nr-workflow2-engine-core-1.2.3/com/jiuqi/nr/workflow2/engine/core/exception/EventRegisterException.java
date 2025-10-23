/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.EventException;

public class EventRegisterException
extends EventException {
    private static final long serialVersionUID = -3994806800639114486L;

    public EventRegisterException(String eventDefinitionId, String message) {
        super(eventDefinitionId, "register action event " + eventDefinitionId + " error: " + message);
    }
}

