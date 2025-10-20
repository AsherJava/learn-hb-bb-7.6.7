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

public class ConsolidatedSubjectChangedEvent
extends ApplicationEvent {
    public ConsolidatedSubjectChangedEvent(ConsolidatedSubjectChangedInfo source, NpContext context) {
        super(source);
        NpContextHolder.setContext((NpContext)context);
    }

    public ConsolidatedSubjectChangedInfo getConsolidatedSubjectChangedInfo() {
        if (this.getSource() != null) {
            return (ConsolidatedSubjectChangedInfo)this.getSource();
        }
        return null;
    }

    public static class ConsolidatedSubjectChangedInfo {
        private final String systemId;

        public ConsolidatedSubjectChangedInfo(String systemId) {
            this.systemId = systemId;
        }

        public String getSystemId() {
            return this.systemId;
        }
    }
}

