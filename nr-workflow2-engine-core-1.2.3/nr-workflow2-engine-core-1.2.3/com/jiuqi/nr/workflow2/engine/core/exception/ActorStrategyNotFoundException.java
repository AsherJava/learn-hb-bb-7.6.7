/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ActorStrategyException;

public class ActorStrategyNotFoundException
extends ActorStrategyException {
    private static final long serialVersionUID = 3183398168354735134L;

    public ActorStrategyNotFoundException(String actorStrategyDefinitionId) {
        super(actorStrategyDefinitionId, "actor strategy " + actorStrategyDefinitionId + " not found.");
    }
}

