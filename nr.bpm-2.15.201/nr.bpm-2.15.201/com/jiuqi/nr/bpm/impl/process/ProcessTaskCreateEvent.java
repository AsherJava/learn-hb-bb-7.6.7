/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.internal.impl.FlowsType;

public class ProcessTaskCreateEvent
extends TaskCreateEvent {
    private BusinessKey businessKey;
    private String actionId;
    private String taskId;
    private Boolean forceUpload = false;
    private IConditionCache conditionCache;

    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getForceUpload() {
        return this.forceUpload;
    }

    public void setForceUpload(Boolean forceUpload) {
        this.forceUpload = forceUpload;
    }

    @Override
    public FlowsType getFlowsType() {
        return FlowsType.DEFAULT;
    }

    public IConditionCache getConditionCache() {
        return this.conditionCache;
    }

    public void setConditionCache(IConditionCache conditionCache) {
        this.conditionCache = conditionCache;
    }
}

