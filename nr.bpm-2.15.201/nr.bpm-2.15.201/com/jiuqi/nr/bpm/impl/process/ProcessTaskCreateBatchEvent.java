/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import java.util.List;

public class ProcessTaskCreateBatchEvent
extends TaskCreateEvent {
    private List<BusinessKey> businessKey;
    private String actionId;

    public List<BusinessKey> getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(List<BusinessKey> businessKey) {
        this.businessKey = businessKey;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public FlowsType getFlowsType() {
        return FlowsType.DEFAULT;
    }
}

