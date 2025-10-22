/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.rule.floatline;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.floatline.FloatLineRuleExecutorImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GcCalcFloatLineRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    @Override
    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    @Override
    protected void execute(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        new FloatLineRuleExecutorImpl().calMerge(rule, env);
    }

    @Override
    public boolean isMatch(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!(rule instanceof FloatLineRuleDTO)) {
            return false;
        }
        return RuleTypeEnum.FLOAT_LINE.getCode().equals(rule.getRuleType());
    }
}

