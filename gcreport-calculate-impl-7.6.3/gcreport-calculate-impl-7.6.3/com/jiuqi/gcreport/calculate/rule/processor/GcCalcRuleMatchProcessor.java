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
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import javax.validation.constraints.NotNull;

public interface GcCalcRuleMatchProcessor {
    public boolean isMatch(@NotNull AbstractUnionRule var1, GcCalcEnvContext var2);

    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz();
}

