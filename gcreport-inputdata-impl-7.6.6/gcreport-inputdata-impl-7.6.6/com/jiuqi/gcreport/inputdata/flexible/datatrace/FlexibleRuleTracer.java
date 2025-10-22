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
package com.jiuqi.gcreport.inputdata.flexible.datatrace;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTracer;
import com.jiuqi.gcreport.inputdata.flexible.datatrace.FlexibleRuleTraceProcessor;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import org.springframework.stereotype.Component;

@Component
public class FlexibleRuleTracer
implements UnionRuleTracer {
    public static final String RULE_TYPE_NAME = "FLEXIBLE";

    public String getRuleType() {
        return RULE_TYPE_NAME;
    }

    public UnionRuleTraceProcessor newProcessorInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        return FlexibleRuleTraceProcessor.newInstance(offsetItem, rule, taskArg);
    }
}

