/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.impl.Actor.JsonUtils;

abstract class ActorStrategyBase<T extends ActorStrategyParameter>
implements ActorStrategy<T> {
    ActorStrategyBase() {
    }

    @Override
    public abstract Class<? extends T> getParameterType();

    @Override
    public String serializeParameter(T parameter) throws Exception {
        return JsonUtils.toString(parameter);
    }

    @Override
    public T readParameter(String parameterJson) throws Exception {
        if (parameterJson == null) {
            return null;
        }
        return (T)((ActorStrategyParameter)JsonUtils.toObject(parameterJson, this.getParameterType()));
    }
}

