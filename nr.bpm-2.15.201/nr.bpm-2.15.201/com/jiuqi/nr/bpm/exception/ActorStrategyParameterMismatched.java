/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.exception.BpmException;

public class ActorStrategyParameterMismatched
extends BpmException {
    private static final long serialVersionUID = 6152628346509155291L;

    public ActorStrategyParameterMismatched(String actorStrategyType) {
        super(String.format("actor startegy %s parameter text mismatched parameter text.", actorStrategyType));
    }
}

