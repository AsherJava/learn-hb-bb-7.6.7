/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.event;

import com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData;
import org.springframework.context.ApplicationEvent;

public class FinancialStatusGroupChangeEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -6196467161525035319L;

    public FinancialStatusGroupChangeEvent(FinancialStatusChangeEventData source) {
        super(source);
    }

    @Override
    public FinancialStatusChangeEventData getSource() {
        return (FinancialStatusChangeEventData)this.source;
    }
}

