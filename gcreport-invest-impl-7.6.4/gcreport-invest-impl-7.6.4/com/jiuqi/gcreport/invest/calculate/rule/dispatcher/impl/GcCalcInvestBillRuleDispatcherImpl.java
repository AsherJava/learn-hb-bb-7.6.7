/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.AbstarctGcCalcRuleDispatcher
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.enums.GcCalcRuleDispatcherPriorityEnum
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.invest.calculate.rule.dispatcher.impl;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.AbstarctGcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.enums.GcCalcRuleDispatcherPriorityEnum;
import com.jiuqi.gcreport.invest.calculate.rule.dispatcher.executor.GcCalcInvestBillRuleDispatcherExecutor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcInvestBillRuleDispatcherImpl
extends AbstarctGcCalcRuleDispatcher {
    @Autowired
    private GcCalcInvestBillRuleDispatcherExecutor executor;

    protected void execute(@NotNull List<AbstractUnionRule> rules, GcCalcEnvContext env) throws Exception {
        this.executor.execute(rules, env);
    }

    public GcCalcRuleDispatcherPriorityEnum getDispatcherPriority() {
        return GcCalcRuleDispatcherPriorityEnum.MIN_PRIORITY;
    }
}

