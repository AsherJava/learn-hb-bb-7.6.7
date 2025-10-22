/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import org.springframework.context.ApplicationEvent;

public class RuleChangeEvent
extends ApplicationEvent {
    public RuleChangeEvent(RuleChangedInfo ruleChangedInfo, NpContext context) {
        super(ruleChangedInfo);
        NpContextHolder.setContext((NpContext)context);
    }

    public RuleChangedInfo getFlexRuleChangedInfo() {
        if (this.getSource() != null) {
            return (RuleChangedInfo)this.getSource();
        }
        return null;
    }

    public static class RuleChangedInfo {
        private final String id;
        private final String ruleType;

        public RuleChangedInfo(String id, String ruleType) {
            this.id = id;
            this.ruleType = ruleType;
        }

        public String getId() {
            return this.id;
        }

        public String getRuleType() {
            return this.ruleType;
        }
    }
}

