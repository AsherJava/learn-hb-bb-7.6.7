/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.job;

import com.jiuqi.nr.summary.executor.sum.SumParam;
import java.io.Serializable;

public class SumExeParam
implements Serializable {
    private SumParam sumParam;
    private boolean afterCalculate;

    public SumParam getSumParam() {
        return this.sumParam;
    }

    public void setSumParam(SumParam sumParam) {
        this.sumParam = sumParam;
    }

    public boolean isAfterCalculate() {
        return this.afterCalculate;
    }

    public void setAfterCalculate(boolean afterCalculate) {
        this.afterCalculate = afterCalculate;
    }
}

