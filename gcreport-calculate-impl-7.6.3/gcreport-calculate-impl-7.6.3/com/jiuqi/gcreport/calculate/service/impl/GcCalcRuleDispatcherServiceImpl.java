/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.common.GcCalcSortAndRegroupRuleMap;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.processor.GcCalcRuleMatchProcessor;
import com.jiuqi.gcreport.calculate.service.GcCalcRuleDispatcherService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GcCalcRuleDispatcherServiceImpl
implements GcCalcRuleDispatcherService {
    @Autowired
    private List<GcCalcRuleMatchProcessor> calcRuleProcessors;

    @Override
    public GcCalcRuleDispatcher findGroupDispatcherByRule(AbstractUnionRule rule, GcCalcEnvContext env) {
        List ruleProcessors = this.calcRuleProcessors.stream().filter(calcRuleProcessor -> {
            boolean isMatch = calcRuleProcessor.isMatch(rule, env);
            return isMatch;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ruleProcessors)) {
            String errorMsg = GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.processor.notfound.error", (Object[])new Object[]{rule.getLocalizedName()});
            throw new BusinessRuntimeException(errorMsg);
        }
        if (ruleProcessors.size() > 1) {
            String processorNames = ruleProcessors.stream().map(ruleMatchProcessor -> ruleMatchProcessor.getClass().getName()).reduce("", (s1, s2) -> s1 + "," + s2);
            String errorMsg = GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.processor.repeat.error", (Object[])new Object[]{rule.getLocalizedName(), ruleProcessors.size(), processorNames});
            throw new BusinessRuntimeException(errorMsg);
        }
        GcCalcRuleMatchProcessor ruleMatchProcessor2 = (GcCalcRuleMatchProcessor)ruleProcessors.get(0);
        Class<? extends GcCalcRuleDispatcher> ruleDispatcherBeanClazz = ruleMatchProcessor2.getRuleDispatcherBeanClazz();
        GcCalcRuleDispatcher calcRuleDispatcher = (GcCalcRuleDispatcher)SpringContextUtils.getBean(ruleDispatcherBeanClazz);
        if (calcRuleDispatcher == null) {
            String errorMsg = GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.processor.notfound.error", (Object[])new Object[]{rule.getLocalizedName()});
            throw new BusinessRuntimeException(errorMsg);
        }
        return calcRuleDispatcher;
    }

    @Override
    public GcCalcSortAndRegroupRuleMap sortAndRegroupRuleDispatchers(GcCalcEnvContext env, List<AbstractUnionRule> rules) {
        GcCalcSortAndRegroupRuleMap sortAndRegroupRuleMap = new GcCalcSortAndRegroupRuleMap();
        sortAndRegroupRuleMap.putAll(env, rules);
        return sortAndRegroupRuleMap;
    }
}

