/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.calculate.rule.fixedTable;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.fixedTable.FixedTableRuleExecutorImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class GcCalcFixedTableRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    @Override
    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    @Override
    public void execute(AbstractUnionRule rule, GcCalcEnvContext env) {
        new FixedTableRuleExecutorImpl().calMerge(rule, env);
    }

    @Override
    public boolean isMatch(AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!(rule instanceof FixedTableRuleDTO)) {
            return false;
        }
        return RuleTypeEnum.FIXED_TABLE.getCode().equals(rule.getRuleType());
    }
}

