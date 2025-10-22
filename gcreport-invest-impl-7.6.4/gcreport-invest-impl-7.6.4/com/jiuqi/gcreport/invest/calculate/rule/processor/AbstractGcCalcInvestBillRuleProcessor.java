/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.rule.processor.GcCalcRuleMatchProcessor
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.processor.GcCalcRuleMatchProcessor;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractGcCalcInvestBillRuleProcessor
implements GcCalcRuleMatchProcessor {
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public final void processor(@NotNull AbstractInvestmentRule rule, GcCalcEnvContext env, List<GcInvestBillGroupDTO> datas, boolean isMonthlyIncrement) {
        if (datas == null) {
            datas = Collections.emptyList();
        }
        this.execute(rule, env, datas, isMonthlyIncrement);
    }

    protected abstract void execute(@NotNull AbstractInvestmentRule var1, GcCalcEnvContext var2, List<GcInvestBillGroupDTO> var3, boolean var4);
}

