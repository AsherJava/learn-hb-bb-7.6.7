/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory
 */
package com.jiuqi.nr.workflow2.actors;

import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.workflow2.actors.executor.SystemUserExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class SystemUserActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.systemuser";
    public static final String TITLE = "\u7cfb\u7edf\u7ba1\u7406\u5458";
    public static final short ORDER = 255;
    private final SystemUserExecutor exxcutor;

    public SystemUserActorStrategy(SystemUserService systemUserService) {
        this.exxcutor = new SystemUserExecutor(systemUserService);
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        return this.exxcutor;
    }
}

