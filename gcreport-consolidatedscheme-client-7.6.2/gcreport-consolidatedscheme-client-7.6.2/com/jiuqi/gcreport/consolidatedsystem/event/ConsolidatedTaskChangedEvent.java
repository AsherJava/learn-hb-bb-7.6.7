/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.consolidatedsystem.event;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.context.ApplicationEvent;

public class ConsolidatedTaskChangedEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public ConsolidatedTaskChangedEvent(ConsolidatedTaskChangedInfo source, NpContext context) {
        super(source);
        NpContextHolder.setContext((NpContext)context);
    }

    public ConsolidatedTaskChangedInfo getConsolidatedTaskChangedInfo() {
        return this.source == null ? null : (ConsolidatedTaskChangedInfo)this.source;
    }

    public static class ConsolidatedTaskChangedInfo {
    }
}

