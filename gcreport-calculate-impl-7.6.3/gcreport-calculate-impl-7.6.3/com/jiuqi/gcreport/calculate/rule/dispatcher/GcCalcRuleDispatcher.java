/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.rule.dispatcher;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.enums.GcCalcRuleDispatcherPriorityEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface GcCalcRuleDispatcher {
    public void dispatch(@NotNull List<AbstractUnionRule> var1, GcCalcEnvContext var2) throws Exception;

    public GcCalcRuleDispatcherPriorityEnum getDispatcherPriority();
}

