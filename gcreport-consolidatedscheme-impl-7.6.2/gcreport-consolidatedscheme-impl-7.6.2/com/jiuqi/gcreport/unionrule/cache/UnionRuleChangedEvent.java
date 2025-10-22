/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.unionrule.cache;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.context.ApplicationEvent;

public class UnionRuleChangedEvent
extends ApplicationEvent {
    public UnionRuleChangedEvent(UnionRuleChangedInfo source, NpContext context) {
        super(source);
        NpContextHolder.setContext((NpContext)context);
    }

    public UnionRuleChangedInfo getUnionRuleChangedInfo() {
        if (this.getSource() != null) {
            return (UnionRuleChangedInfo)this.getSource();
        }
        return null;
    }

    public static class UnionRuleChangedInfo {
        private final String systemId;

        public UnionRuleChangedInfo(String systemId) {
            this.systemId = systemId;
        }

        public String getSystemId() {
            return this.systemId;
        }
    }
}

