/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.actor;

import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;
import java.util.List;

public interface IActorStrategyFactory {
    public List<IActorStrategyDefinition> queryAllActorStrategyDefinition();

    public IActorStrategyDefinition queryActorStrategyDefinition(String var1);

    public IActorStrategyExecutorFactory queryActorStrategyExecutorFactory(String var1);

    public IActorStrategyExecutor getActorStrategyExecutor(String var1, String var2);
}

