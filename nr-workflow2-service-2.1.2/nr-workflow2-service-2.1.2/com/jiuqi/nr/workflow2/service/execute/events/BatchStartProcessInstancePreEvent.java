/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.execute.events;

import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import org.springframework.context.ApplicationEvent;

public class BatchStartProcessInstancePreEvent
extends ApplicationEvent {
    public BatchStartProcessInstancePreEvent(IProcessRunPara envPara) {
        super(envPara);
    }
}

