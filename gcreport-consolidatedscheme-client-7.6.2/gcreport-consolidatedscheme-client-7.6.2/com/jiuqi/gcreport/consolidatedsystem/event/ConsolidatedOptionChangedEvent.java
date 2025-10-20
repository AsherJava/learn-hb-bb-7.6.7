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

public class ConsolidatedOptionChangedEvent
extends ApplicationEvent {
    public ConsolidatedOptionChangedEvent(ConsolidatedOptionChangedInfo consolidatedOptionChangedInfo, NpContext context) {
        super(consolidatedOptionChangedInfo);
        NpContextHolder.setContext((NpContext)context);
    }

    public ConsolidatedOptionChangedInfo getConsolidatedOptionChangedInfo() {
        if (this.getSource() != null) {
            return (ConsolidatedOptionChangedInfo)this.getSource();
        }
        return null;
    }

    public static class ConsolidatedOptionChangedInfo {
        private final String systemId;

        public ConsolidatedOptionChangedInfo(String systemId) {
            this.systemId = systemId;
        }

        public String getSystemId() {
            return this.systemId;
        }
    }
}

