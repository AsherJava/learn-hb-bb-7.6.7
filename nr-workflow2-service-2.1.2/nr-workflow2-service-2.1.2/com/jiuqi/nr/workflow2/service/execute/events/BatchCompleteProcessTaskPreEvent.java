/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.service.execute.events;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import org.springframework.context.ApplicationEvent;

public class BatchCompleteProcessTaskPreEvent
extends ApplicationEvent {
    private final IBusinessKeyCollection businessKeyCollection;
    private final IProcessExecutePara executePara;

    public BatchCompleteProcessTaskPreEvent(IProcessExecutePara executePara, IBusinessKeyCollection businessKeyCollection) {
        super("\u6d41\u7a0b\u6d41\u8f6c\u524d\u7f6e\u6d88\u606f\u4e8b\u4ef6");
        this.businessKeyCollection = businessKeyCollection;
        this.executePara = executePara;
    }

    public IBusinessKeyCollection getBusinessKeyCollection() {
        return this.businessKeyCollection;
    }

    public IProcessExecutePara getExecutePara() {
        return this.executePara;
    }
}

