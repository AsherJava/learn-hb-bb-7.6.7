/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.processor.GcCalcRuleMatchProcessor;
import com.jiuqi.gcreport.calculate.service.GcCalcRuleProcessorService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcCalcRuleProcessorServiceImpl
implements GcCalcRuleProcessorService {
    @Autowired
    private List<GcCalcRuleMatchProcessor> ruleProcessors;

    @Override
    public GcCalcRuleMatchProcessor findRuleProcessorByRule(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        List processors = this.ruleProcessors.stream().filter(calcRuleProcessor -> calcRuleProcessor.isMatch(rule, env)).collect(Collectors.toList());
        if (processors == null || processors.size() == 0) {
            String errorMsg = GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.processor.notfound.error", (Object[])new Object[]{rule.getLocalizedName()});
            throw new BusinessRuntimeException(errorMsg);
        }
        if (processors.size() > 1) {
            String processorNames = processors.stream().map(processor -> processor.getClass().getName()).reduce("", (s1, s2) -> s1 + "," + s2);
            String errorMsg = GcI18nUtil.getMessage((String)"gc.calculate.calc.rule.processor.repeat.error", (Object[])new Object[]{rule.getLocalizedName(), processors.size(), processorNames});
            throw new BusinessRuntimeException(errorMsg);
        }
        return (GcCalcRuleMatchProcessor)processors.get(0);
    }
}

