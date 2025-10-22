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

public class ConsolidatedSystemChangedEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 3832156012833224231L;

    public ConsolidatedSystemChangedEvent(ConsolidatedSystemChangedInfo source, NpContext context) {
        super(source);
        NpContextHolder.setContext((NpContext)context);
    }

    public ConsolidatedSystemChangedInfo getConsolidatedSystemChangedInfo() {
        if (this.getSource() != null) {
            return (ConsolidatedSystemChangedInfo)this.getSource();
        }
        return null;
    }

    public static class ConsolidatedSystemChangedInfo {
        private final String dataSchemeKey;

        public ConsolidatedSystemChangedInfo(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }
    }
}

