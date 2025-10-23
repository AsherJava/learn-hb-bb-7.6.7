/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api;

import com.jiuqi.nr.summary.api.BasicGetter;
import com.jiuqi.nr.summary.api.Ordered;

public interface SummaryFormula
extends BasicGetter,
Ordered {
    public String getSummarySolutionKey();

    public String getSummaryReportKey();

    public boolean useCalculate();

    public boolean useCheck();

    public int getCheckType();

    public String getExpression();
}

