/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import java.util.Date;

public class ProcessInstanceImpl
implements ProcessInstance {
    private BusinessKey businessKey;

    public ProcessInstanceImpl(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String getId() {
        return BusinessKeyFormatter.formatToString(this.businessKey);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getProcessDefinitionId() {
        return null;
    }

    @Override
    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    @Override
    public ProcessInstance.ProcessInstanceState getState() {
        return ProcessInstance.ProcessInstanceState.ACTIVE;
    }

    @Override
    public Date getStartTime() {
        return null;
    }
}

