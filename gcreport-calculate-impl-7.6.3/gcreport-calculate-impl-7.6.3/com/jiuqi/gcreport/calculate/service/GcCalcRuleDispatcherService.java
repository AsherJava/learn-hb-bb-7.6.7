/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.calculate.common.GcCalcSortAndRegroupRuleMap;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;

public interface GcCalcRuleDispatcherService {
    public GcCalcRuleDispatcher findGroupDispatcherByRule(AbstractUnionRule var1, GcCalcEnvContext var2);

    public GcCalcSortAndRegroupRuleMap sortAndRegroupRuleDispatchers(GcCalcEnvContext var1, List<AbstractUnionRule> var2);
}

