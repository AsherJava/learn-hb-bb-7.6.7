/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.task.callback;

import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;

public interface GcCalcRuleProcessorForkJoinTaskCallBack<T extends AbstractUnionRule> {
    public void run(T var1);
}

