/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

public class ActorStrategyException
extends RuntimeException {
    private static final long serialVersionUID = 5442066106445724533L;
    private final String actorStrategyDefinitionId;

    public String getActorStrategyDefinitionId() {
        return this.actorStrategyDefinitionId;
    }

    public ActorStrategyException(String actorStrategyDefinitionId, String message) {
        super(message);
        this.actorStrategyDefinitionId = actorStrategyDefinitionId;
    }
}

