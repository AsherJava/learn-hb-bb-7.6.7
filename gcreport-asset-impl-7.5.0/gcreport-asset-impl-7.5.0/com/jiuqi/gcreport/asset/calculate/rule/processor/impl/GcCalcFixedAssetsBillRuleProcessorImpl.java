/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher
 *  com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl
 *  com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.asset.calculate.rule.processor.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.asset.calculate.rule.processor.executor.GcCalcFixedAssetsBillRuleExecutor;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.impl.GcCalcRuleDispatcherImpl;
import com.jiuqi.gcreport.calculate.rule.processor.AbstractGcCalcRuleProcessor;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcFixedAssetsBillRuleProcessorImpl
extends AbstractGcCalcRuleProcessor {
    @Autowired
    private GcCalcFixedAssetsBillRuleExecutor executor;

    public Class<? extends GcCalcRuleDispatcher> getRuleDispatcherBeanClazz() {
        return GcCalcRuleDispatcherImpl.class;
    }

    public void execute(AbstractUnionRule rule, GcCalcEnvContext env) {
        this.executor.execute(rule, env);
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    public boolean isMatch(AbstractUnionRule rule, GcCalcEnvContext env) {
        if (!(rule instanceof FixedAssetsRuleDTO)) {
            return false;
        }
        return RuleTypeEnum.FIXED_ASSETS.getCode().equals(rule.getRuleType());
    }
}

