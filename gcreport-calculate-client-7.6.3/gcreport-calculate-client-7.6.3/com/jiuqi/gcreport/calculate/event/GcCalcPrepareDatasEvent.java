/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.event;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class GcCalcPrepareDatasEvent
extends ApplicationEvent {
    private final List<AbstractUnionRule> rules;
    private GcCalcEnvContext env;

    public GcCalcPrepareDatasEvent(Object source, GcCalcEnvContext env, List<AbstractUnionRule> rules) {
        super(source);
        this.env = env;
        this.rules = rules;
    }

    public List<AbstractUnionRule> getRules() {
        return this.rules;
    }

    public GcCalcEnvContext getEnv() {
        return this.env;
    }

    public void setEnv(GcCalcEnvContext env) {
        this.env = env;
    }
}

