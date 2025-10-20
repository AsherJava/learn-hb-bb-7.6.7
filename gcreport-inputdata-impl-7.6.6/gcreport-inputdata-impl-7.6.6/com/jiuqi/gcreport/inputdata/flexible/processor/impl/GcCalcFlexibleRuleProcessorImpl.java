/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl
 *  com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.impl;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.FlexibleRuleExecutorImpl;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcFlexibleRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    @Transactional(rollbackFor={Exception.class})
    public void execute(AbstractUnionRule rule, GcCalcEnvContext env) {
        new FlexibleRuleExecutorImpl().calMerge(rule, env);
    }

    public boolean isMatch(AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!(rule instanceof FlexibleRuleDTO)) {
            return false;
        }
        return RuleTypeEnum.FLEXIBLE.getCode().equals(rule.getRuleType());
    }
}

