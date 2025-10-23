/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;

public abstract class ExecutorBase
implements IActorStrategyExecutor {
    abstract boolean isActive();

    abstract boolean isDillwithBusinessKey();
}

