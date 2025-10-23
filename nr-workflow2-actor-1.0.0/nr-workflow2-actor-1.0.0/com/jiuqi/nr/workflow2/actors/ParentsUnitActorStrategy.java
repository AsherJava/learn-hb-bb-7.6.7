/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory
 */
package com.jiuqi.nr.workflow2.actors;

import com.jiuqi.nr.workflow2.actors.executor.CurrentUnitEexcutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class ParentsUnitActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.parentsunit";
    public static final String TITLE = "\u6240\u6709\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u7684\u7528\u6237";
    public static final short ORDER = 14;
    private final CurrentUnitEexcutor currentUnitEexcutor;

    public ParentsUnitActorStrategy(CurrentUnitEexcutor currentUnitEexcutor) {
        this.currentUnitEexcutor = currentUnitEexcutor;
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        return this.currentUnitEexcutor;
    }
}

