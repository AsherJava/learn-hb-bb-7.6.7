/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 */
package com.jiuqi.nr.workflow2.service.execute.events;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import org.springframework.context.ApplicationEvent;

public class CompleteProcessTaskPosEvent
extends ApplicationEvent {
    private final IBusinessKey businessKey;
    private final IProcessExecutePara executePara;

    public CompleteProcessTaskPosEvent(IProcessExecutePara executePara, IBusinessKey businessKey) {
        super("\u6d41\u7a0b\u6d41\u8f6c\u540e\u7f6e\u6d88\u606f\u4e8b\u4ef6");
        this.businessKey = businessKey;
        this.executePara = executePara;
    }

    public IBusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public IProcessExecutePara getExecutePara() {
        return this.executePara;
    }
}

