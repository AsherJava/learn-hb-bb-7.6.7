/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory
 */
package com.jiuqi.nr.workflow2.actors;

import com.jiuqi.nr.workflow2.actors.executor.AuthorityEexcutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class CanSubmitActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.cansubmit";
    public static final String TITLE = "\u6709\u9001\u5ba1\u6743\u9650\u7684\u4eba";
    public static final short ORDER = 1;
    private final AuthorityEexcutor executor;

    public CanSubmitActorStrategy(AuthorityEexcutor executor) {
        this.executor = executor;
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        return this.executor;
    }
}

