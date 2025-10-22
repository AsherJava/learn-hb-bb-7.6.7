/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl
 *  com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper.FinancialCheckBalanceExecutorHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper.FinancialCheckItemExecutorHelper;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GcCalcFinancialCheckRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    protected void execute(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        ReconciliationModeEnum checkWay = FinancialCheckConfigUtils.getCheckWay();
        if (ReconciliationModeEnum.ITEM.equals((Object)checkWay)) {
            new FinancialCheckItemExecutorHelper(rule, env.getCalcArgments()).calMerge(env);
        } else {
            new FinancialCheckBalanceExecutorHelper(rule, env.getCalcArgments()).calMerge(env);
        }
    }

    public boolean isMatch(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!(rule instanceof FinancialCheckRuleDTO)) {
            return false;
        }
        return RuleTypeEnum.FINANCIAL_CHECK.getCode().equals(rule.getRuleType());
    }
}

