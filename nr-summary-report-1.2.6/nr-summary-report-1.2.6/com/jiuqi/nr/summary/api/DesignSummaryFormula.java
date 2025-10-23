/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api;

import com.jiuqi.nr.summary.api.BasicSetter;
import com.jiuqi.nr.summary.api.OrderSetter;
import com.jiuqi.nr.summary.api.SummaryFormula;

public interface DesignSummaryFormula
extends SummaryFormula,
BasicSetter,
OrderSetter {
    public void setSummarySolutionKey(String var1);

    public void setSummaryReportKey(String var1);

    public void setUseCalculate(boolean var1);

    public void setUseCheck(boolean var1);

    public void setCheckType(int var1);

    public void setExpression(String var1);
}

