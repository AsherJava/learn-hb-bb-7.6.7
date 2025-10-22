/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.rule.dispatcher;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import javax.validation.constraints.NotNull;

public abstract class AbstarctGcCalcRuleDispatcher
implements GcCalcRuleDispatcher {
    @Override
    public final void dispatch(List<AbstractUnionRule> rules, GcCalcEnvContext env) throws Exception {
        if (rules == null || rules.size() == 0) {
            return;
        }
        this.execute(rules, env);
    }

    protected abstract void execute(@NotNull List<AbstractUnionRule> var1, GcCalcEnvContext var2) throws Exception;
}

