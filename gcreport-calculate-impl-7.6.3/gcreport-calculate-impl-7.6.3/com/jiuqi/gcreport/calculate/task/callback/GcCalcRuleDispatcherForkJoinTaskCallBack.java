/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.calculate.task.callback;

import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;

public interface GcCalcRuleDispatcherForkJoinTaskCallBack<T extends GcCalcRuleDispatcher> {
    public void run(T var1);
}

