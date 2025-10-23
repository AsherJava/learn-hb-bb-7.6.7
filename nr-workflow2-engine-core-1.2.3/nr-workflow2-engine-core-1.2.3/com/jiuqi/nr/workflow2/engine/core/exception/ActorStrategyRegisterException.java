/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

import com.jiuqi.nr.workflow2.engine.core.exception.ActorStrategyException;

public class ActorStrategyRegisterException
extends ActorStrategyException {
    private static final long serialVersionUID = -4795635567415227684L;

    public ActorStrategyRegisterException(String actorStrategyDefintionId, String message) {
        super(actorStrategyDefintionId, "register actor strategy " + actorStrategyDefintionId + " error: " + message);
    }
}

