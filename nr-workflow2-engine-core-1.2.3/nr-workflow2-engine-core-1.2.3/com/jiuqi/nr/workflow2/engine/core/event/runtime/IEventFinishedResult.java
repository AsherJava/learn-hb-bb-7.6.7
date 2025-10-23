/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;

public interface IEventFinishedResult {
    default public CompleteDependentType getCompleteDependentType() {
        return CompleteDependentType.NONE;
    }

    public EventExecutionStatus getFinishStatus();

    public EventExecutionAffect getAffectStatus();
}

