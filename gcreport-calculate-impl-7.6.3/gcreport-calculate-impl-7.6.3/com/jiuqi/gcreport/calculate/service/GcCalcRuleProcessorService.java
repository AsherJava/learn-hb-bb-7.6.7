/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.processor.GcCalcRuleMatchProcessor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;

public interface GcCalcRuleProcessorService {
    public GcCalcRuleMatchProcessor findRuleProcessorByRule(AbstractUnionRule var1, GcCalcEnvContext var2);
}

