/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.rule.dispatcher.executor;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.calculate.service.GcCalcRuleProcessorService;
import com.jiuqi.gcreport.calculate.task.GcCalcRuleProcessorForkJoinTask;
import com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcRuleDispatcherExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcRuleDispatcherExecutor.class);
    @Autowired
    private GcCalcRuleProcessorService ruleProcessorService;

    public void execute(List<AbstractUnionRule> rules, GcCalcEnvContext env) throws Exception {
        if (rules == null || rules.size() == 0) {
            return;
        }
        GcCalcRuleProcessorForkJoinTask<AbstractUnionRule> forkJoinTask = new GcCalcRuleProcessorForkJoinTask<AbstractUnionRule>(env, rules, unionRule -> this.executeRuleProcessor(env, unionRule));
        ForkJoinTask<String> submit = GcCalcRuleUtils.getRuleProcessorForkJoinPool().submit(forkJoinTask);
        this.logger.debug("\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u5408\u5e76\u89c4\u5219\u6807\u9898\u4fe1\u606f\uff1a" + submit.get());
    }

    private void executeRuleProcessor(GcCalcEnvContext env, AbstractUnionRule rule) {
        AbstractGcCalcRuleProcessor calcRuleProcessor = (AbstractGcCalcRuleProcessor)this.ruleProcessorService.findRuleProcessorByRule(rule, env);
        calcRuleProcessor.processor(rule, env);
    }
}

