/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api;

import com.jiuqi.nr.data.logic.api.param.CalExeResult;
import com.jiuqi.nr.data.logic.api.param.CalPar;
import com.jiuqi.nr.data.logic.spi.ICalculateMonitor;

public interface ICalculateService {
    public CalExeResult calculate(CalPar var1);

    public CalExeResult calculate(CalPar var1, ICalculateMonitor var2);
}

