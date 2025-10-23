/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.actor;

import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.Set;

public interface IActorStrategyExecutor {
    public boolean isMatch(IActor var1, RuntimeBusinessKey var2);

    public Set<String> getMatchUsers(RuntimeBusinessKey var1);

    public IBusinessObjectSet getMatchBusinessKeys(IActor var1, RuntimeBusinessKeyCollection var2);
}

