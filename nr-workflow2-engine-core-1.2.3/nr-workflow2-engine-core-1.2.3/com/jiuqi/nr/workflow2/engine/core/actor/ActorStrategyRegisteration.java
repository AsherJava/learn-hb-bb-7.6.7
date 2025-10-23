/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.actor;

import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class ActorStrategyRegisteration
implements IActorStrategyDefinition {
    private String id;
    private String title;
    private String description;
    private short order;
    private IActorStrategyExecutorFactory executorFactory;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public short getOrder() {
        return this.order;
    }

    public IActorStrategyExecutorFactory getExecutorFactory() {
        return this.executorFactory;
    }

    public ActorStrategyRegisteration(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ActorStrategyRegisteration description(String description) {
        this.description = description;
        return this;
    }

    public ActorStrategyRegisteration order(short order) {
        this.order = order;
        return this;
    }

    public ActorStrategyRegisteration executorFactory(IActorStrategyExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
        return this;
    }
}

