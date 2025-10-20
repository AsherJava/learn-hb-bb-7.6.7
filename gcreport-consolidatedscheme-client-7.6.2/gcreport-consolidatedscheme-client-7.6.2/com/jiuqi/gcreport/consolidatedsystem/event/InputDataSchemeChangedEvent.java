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

public class InputDataSchemeChangedEvent
extends ApplicationEvent {
    public InputDataSchemeChangedEvent(InputDataSchemeChangedInfo source, NpContext context) {
        super(source);
        NpContextHolder.setContext((NpContext)context);
    }

    public InputDataSchemeChangedInfo getInputDataSchemeChangedInfo() {
        if (this.getSource() != null) {
            return (InputDataSchemeChangedInfo)this.getSource();
        }
        return null;
    }

    public static class InputDataSchemeChangedInfo {
        private final String dataSchemeKey;

        public InputDataSchemeChangedInfo(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }
    }
}

