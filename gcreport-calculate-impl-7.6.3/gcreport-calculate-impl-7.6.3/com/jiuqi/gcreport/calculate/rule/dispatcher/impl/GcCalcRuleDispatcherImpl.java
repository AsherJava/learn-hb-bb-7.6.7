/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.rule.dispatcher.impl;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.AbstarctGcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.enums.GcCalcRuleDispatcherPriorityEnum;
import com.jiuqi.gcreport.calculate.rule.dispatcher.executor.GcCalcRuleDispatcherExecutor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcRuleDispatcherImpl
extends AbstarctGcCalcRuleDispatcher {
    @Autowired
    private GcCalcRuleDispatcherExecutor executor;

    @Override
    protected void execute(@NotNull List<AbstractUnionRule> rules, GcCalcEnvContext env) throws Exception {
        this.executor.execute(rules, env);
    }

    @Override
    public GcCalcRuleDispatcherPriorityEnum getDispatcherPriority() {
        return GcCalcRuleDispatcherPriorityEnum.MAX_PRIORITY;
    }
}

