/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.actor;

import com.jiuqi.nr.workflow2.engine.core.actor.ActorStrategyRegisteration;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.exception.ActorStrategyNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.ActorStrategyRegisterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorStrategyFactoryImpl
implements IActorStrategyFactory {
    private final List<ActorStrategyRegisteration> srotedActorStrategies = new ArrayList<ActorStrategyRegisteration>();
    private final Map<String, ActorStrategyRegisteration> actorStrategies = new HashMap<String, ActorStrategyRegisteration>();

    public ActorStrategyFactoryImpl(List<ActorStrategyRegisteration> registerations) {
        registerations.sort((r1, r2) -> r1.getOrder() - r2.getOrder());
        for (ActorStrategyRegisteration registeration : registerations) {
            this.validateRegisteration(registeration);
            if (this.actorStrategies.containsKey(registeration.getId())) {
                throw new ActorStrategyRegisterException(registeration.getId(), "actor strategy id '" + registeration.getId() + "' already exists.");
            }
            this.srotedActorStrategies.add(registeration);
            this.actorStrategies.put(registeration.getId(), registeration);
        }
    }

    private void validateRegisteration(ActorStrategyRegisteration registeration) {
        if (registeration.getId() == null || registeration.getId().trim().length() == 0) {
            throw new ActorStrategyRegisterException("", "actor strategy id must not be empty: " + registeration.getTitle());
        }
        if (registeration.getTitle() == null || registeration.getTitle().trim().length() == 0) {
            throw new ActorStrategyRegisterException(registeration.getId(), "actor strategy title must not be empty: " + registeration.getId());
        }
        if (registeration.getExecutorFactory() == null) {
            throw new ActorStrategyRegisterException(registeration.getId(), "actor strategy excutor factory must not be null: " + registeration.getId());
        }
    }

    @Override
    public List<IActorStrategyDefinition> queryAllActorStrategyDefinition() {
        return Collections.unmodifiableList(this.srotedActorStrategies);
    }

    @Override
    public IActorStrategyDefinition queryActorStrategyDefinition(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("args name con not be empty.");
        }
        return this.actorStrategies.get(id);
    }

    @Override
    public IActorStrategyExecutorFactory queryActorStrategyExecutorFactory(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("args name con not be empty.");
        }
        ActorStrategyRegisteration strategy = this.actorStrategies.get(id);
        if (strategy == null) {
            throw new ActorStrategyNotFoundException(id);
        }
        return strategy.getExecutorFactory();
    }

    @Override
    public IActorStrategyExecutor getActorStrategyExecutor(String id, String settings) {
        return this.queryActorStrategyExecutorFactory(id).createExecutor(settings);
    }
}

