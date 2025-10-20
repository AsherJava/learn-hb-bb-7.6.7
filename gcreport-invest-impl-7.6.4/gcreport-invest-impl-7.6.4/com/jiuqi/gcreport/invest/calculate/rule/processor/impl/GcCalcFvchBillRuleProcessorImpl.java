/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl
 *  com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcFvchBillRuleExecutor;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcFvchBillRuleMonthlyExecutor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcFvchBillRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    @Autowired
    GcCalcFvchBillRuleExecutor gcCalcFvchBillRuleExecutor;
    @Autowired
    GcCalcFvchBillRuleMonthlyExecutor gcCalcFvchBillRuleMonthlyExecutor;
    @Autowired
    private ConsolidatedOptionService optionService;

    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    public boolean isMatch(AbstractUnionRule rule, GcCalcEnvContext env) {
        return RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode().equals(rule.getRuleType());
    }

    protected void execute(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        if (this.isMonthly(env)) {
            this.gcCalcFvchBillRuleMonthlyExecutor.calMerge(rule, env);
            return;
        }
        this.gcCalcFvchBillRuleExecutor.calMerge(rule, env);
    }

    private boolean isMonthly(GcCalcEnvContext env) {
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(env.getCalcArgments().getSchemeId(), env.getCalcArgments().getPeriodStr());
        if (reportSystemId == null) {
            return false;
        }
        ConsolidatedOptionVO conOptionBySystemId = this.optionService.getOptionData(reportSystemId);
        return conOptionBySystemId != null && conOptionBySystemId.getMonthlyIncrement() != false;
    }
}

