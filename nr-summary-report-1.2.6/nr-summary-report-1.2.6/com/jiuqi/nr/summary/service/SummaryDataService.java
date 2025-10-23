/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.service;

import com.jiuqi.nr.summary.executor.SummaryExecuteException;
import com.jiuqi.nr.summary.executor.sum.SumParam;

public interface SummaryDataService {
    public void executeSum(SumParam var1, boolean var2) throws SummaryExecuteException;

    public void executeCalculate(SumParam var1) throws SummaryExecuteException;
}

