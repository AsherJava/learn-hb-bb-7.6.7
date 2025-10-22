/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.runtime.ProcessInstance
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import java.util.Date;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.util.Assert;

class ProcessInstanceWrapper
implements com.jiuqi.nr.bpm.common.ProcessInstance {
    private final ProcessInstance innerProcessInstance;

    public ProcessInstanceWrapper(ProcessInstance innerProcessInstance) {
        Assert.notNull((Object)innerProcessInstance, "'innerProcessInstance' must not be null.");
        this.innerProcessInstance = innerProcessInstance;
    }

    @Override
    public String getId() {
        return this.innerProcessInstance.getProcessInstanceId();
    }

    @Override
    public String getName() {
        return this.innerProcessInstance.getName();
    }

    @Override
    public String getProcessDefinitionId() {
        return this.innerProcessInstance.getProcessDefinitionId();
    }

    @Override
    public ProcessInstance.ProcessInstanceState getState() {
        return this.innerProcessInstance.isSuspended() ? ProcessInstance.ProcessInstanceState.PENDING : ProcessInstance.ProcessInstanceState.ACTIVE;
    }

    @Override
    public BusinessKey getBusinessKey() {
        return BusinessKeyFormatter.parsingFromString(this.innerProcessInstance.getBusinessKey());
    }

    @Override
    public Date getStartTime() {
        return this.innerProcessInstance.getStartTime();
    }
}

