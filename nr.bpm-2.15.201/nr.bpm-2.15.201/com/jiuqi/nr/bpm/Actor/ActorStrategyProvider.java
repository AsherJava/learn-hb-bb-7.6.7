/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.Actor;

import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyParameterType;
import java.util.List;
import java.util.Set;

public interface ActorStrategyProvider {
    public ActorStrategy<?> getActorStrategyByType(String var1);

    public List<ActorStrategy<?>> getAllActorStrategies(boolean var1);

    public ActorStrategyParameterType getActorStrategyParameterType(ActorStrategy<?> var1);

    public void refreshActorsByFormSchemeKey(String var1, String var2);

    public void refreshActorsByBusinessKey(String var1);

    public void refreshActorsByProcessKey(String var1);

    public void refreshActorsByProcessInstanceId(String var1);

    public void sendTodoMessage(Task var1, Set<String> var2, String var3);

    public void sendTodoMessage(Task var1, UserTask var2, String var3);
}

