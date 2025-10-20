/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTracer
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.financialcheckImpl.datatrace.base;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTracer;
import com.jiuqi.gcreport.financialcheckImpl.datatrace.base.FinancialCheckUnionRuleTraceProcessor;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import org.springframework.stereotype.Component;

@Component
public class FinancialCheckUnionRuleTracer
implements UnionRuleTracer {
    public String getRuleType() {
        return "FINANCIAL_CHECK";
    }

    public UnionRuleTraceProcessor newProcessorInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        return FinancialCheckUnionRuleTraceProcessor.newInstance(offsetItem, rule, taskArg);
    }
}

