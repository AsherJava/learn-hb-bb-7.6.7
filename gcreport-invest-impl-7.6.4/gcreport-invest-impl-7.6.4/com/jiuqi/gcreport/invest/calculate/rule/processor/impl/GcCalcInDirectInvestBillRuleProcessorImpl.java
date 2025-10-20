/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.impl;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.invest.calculate.rule.dispatcher.impl.GcCalcInvestBillRuleDispatcherImpl;
import com.jiuqi.gcreport.invest.calculate.rule.processor.AbstractGcCalcInvestBillRuleProcessor;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcInvestBillRuleExecutor;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcInvestBillRuleMonthlyExecutor;
import com.jiuqi.gcreport.invest.debug.BillDebugParam;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcInDirectInvestBillRuleProcessorImpl
extends AbstractGcCalcInvestBillRuleProcessor {
    @Autowired
    private GcCalcInvestBillRuleExecutor executor;
    @Autowired
    private GcCalcInvestBillRuleMonthlyExecutor monthlyExecutor;

    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcInvestBillRuleDispatcherImpl.class;
    }

    public boolean isMatch(AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!BillDebugParam.isEnableBill()) {
            return false;
        }
        return RuleTypeEnum.INDIRECT_INVESTMENT.getCode().equals(rule.getRuleType());
    }

    @Override
    protected void execute(@NotNull AbstractInvestmentRule rule, GcCalcEnvContext env, List<GcInvestBillGroupDTO> inDirectInvestmentEOs, boolean isMonthlyIncrement) {
        if (isMonthlyIncrement) {
            this.monthlyExecutor.execute(rule, env, inDirectInvestmentEOs);
        } else {
            this.executor.execute(rule, env, inDirectInvestmentEOs);
        }
    }
}

