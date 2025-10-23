/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory
 */
package com.jiuqi.nr.workflow2.actors;

import com.jiuqi.nr.workflow2.actors.executor.AuthorityEexcutor;
import com.jiuqi.nr.workflow2.actors.executor.CurrentUnitEexcutor;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorCollection;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class ParentUnitCanAuditActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.parentunit_canaudit";
    public static final String TITLE = "\u76f4\u63a5\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u6709\u5ba1\u6279\u6743\u9650\u7684\u4eba";
    public static final short ORDER = 12;
    private final ExecutorCollection executors;

    public ParentUnitCanAuditActorStrategy(CurrentUnitEexcutor currentUnitEexcutor, AuthorityEexcutor authorityExecutor) {
        this.executors = new ExecutorCollection(currentUnitEexcutor, authorityExecutor, ExecutorCollection.MergeMode.OVERLAP);
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        return this.executors;
    }
}

