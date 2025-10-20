/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.rule.processor;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.processor.GcCalcRuleMatchProcessor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import javax.validation.constraints.NotNull;

public abstract class AbstractGcCalcRuleProcessor
implements GcCalcRuleMatchProcessor {
    public void processor(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        this.execute(rule, env);
    }

    protected abstract void execute(@NotNull AbstractUnionRule var1, GcCalcEnvContext var2);
}

