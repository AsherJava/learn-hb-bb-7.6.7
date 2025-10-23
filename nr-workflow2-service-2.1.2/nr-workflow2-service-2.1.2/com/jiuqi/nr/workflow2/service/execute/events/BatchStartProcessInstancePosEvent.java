/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.execute.events;

import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import org.springframework.context.ApplicationEvent;

public class BatchStartProcessInstancePosEvent
extends ApplicationEvent {
    public BatchStartProcessInstancePosEvent(IProcessRunPara envPara) {
        super(envPara);
    }
}

