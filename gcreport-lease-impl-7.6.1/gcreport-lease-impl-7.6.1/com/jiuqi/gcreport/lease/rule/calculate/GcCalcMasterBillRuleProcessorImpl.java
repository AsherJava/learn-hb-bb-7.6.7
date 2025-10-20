/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl
 *  com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.lease.rule.calculate;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.lease.rule.calculate.GcCalcMasterBillRuleExecutor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcMasterBillRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    @Autowired
    private GcCalcMasterBillRuleExecutor executor;

    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    public void execute(AbstractUnionRule rule, GcCalcEnvContext env) {
        this.executor.execute(rule, env);
    }

    public boolean isMatch(AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!(rule instanceof LeaseRuleDTO)) {
            return false;
        }
        return RuleTypeEnum.LEASE.getCode().equals(rule.getRuleType());
    }
}

