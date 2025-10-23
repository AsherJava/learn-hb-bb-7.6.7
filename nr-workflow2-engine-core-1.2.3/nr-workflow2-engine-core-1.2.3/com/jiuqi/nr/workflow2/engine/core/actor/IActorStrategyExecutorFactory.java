/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.actor;

import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;

public interface IActorStrategyExecutorFactory {
    public String getActorStrategyDefinitionId();

    public IActorStrategyExecutor createExecutor(String var1);
}

