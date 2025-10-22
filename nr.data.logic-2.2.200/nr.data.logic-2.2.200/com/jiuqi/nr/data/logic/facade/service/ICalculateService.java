/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.service;

import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalPar;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;

public interface ICalculateService {
    public String calculate(CalculateParam var1);

    public void batchCalculate(CalculateParam var1, IFmlMonitor var2);

    @Deprecated
    public String batchCalculateAsync(CalculateParam var1);

    public void calculate(CalPar var1);
}

