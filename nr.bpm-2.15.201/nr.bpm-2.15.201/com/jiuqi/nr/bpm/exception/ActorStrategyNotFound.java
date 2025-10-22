/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class ActorStrategyNotFound
extends BpmException {
    private static final long serialVersionUID = 3190548295475107478L;

    public ActorStrategyNotFound(String actorStrategyId) {
        super(String.format("actor strategy %s not found.", actorStrategyId));
    }
}

