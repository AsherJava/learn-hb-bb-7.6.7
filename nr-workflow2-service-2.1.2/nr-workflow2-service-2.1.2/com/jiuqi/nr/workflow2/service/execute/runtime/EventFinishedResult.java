/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;

public class EventFinishedResult
implements IEventFinishedResult {
    private EventExecutionStatus finishStatus;
    private EventExecutionAffect affectStatus;
    private CompleteDependentType completeDependentType;

    public EventFinishedResult(EventExecutionStatus finishStatus) {
        this.finishStatus = finishStatus;
    }

    public EventFinishedResult(EventExecutionStatus finishStatus, EventExecutionAffect affectStatus) {
        this(finishStatus);
        this.affectStatus = affectStatus;
    }

    public EventFinishedResult(EventExecutionStatus finishStatus, EventExecutionAffect affectStatus, CompleteDependentType completeDependentType) {
        this(finishStatus, affectStatus);
        this.completeDependentType = completeDependentType;
    }

    public EventExecutionStatus getFinishStatus() {
        return this.finishStatus;
    }

    public void setFinishStatus(EventExecutionStatus finishStatus) {
        this.finishStatus = finishStatus;
    }

    public EventExecutionAffect getAffectStatus() {
        return this.affectStatus;
    }

    public void setAffectStatus(EventExecutionAffect affectStatus) {
        this.affectStatus = affectStatus;
    }

    public CompleteDependentType getCompleteDependentType() {
        return this.completeDependentType;
    }

    public void setCompleteDependentType(CompleteDependentType completeDependentType) {
        this.completeDependentType = completeDependentType;
    }
}

