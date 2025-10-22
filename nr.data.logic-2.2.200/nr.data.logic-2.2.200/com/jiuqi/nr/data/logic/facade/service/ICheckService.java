/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.service;

import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;

public interface ICheckService {
    public CheckResult check(CheckParam var1);

    public String allCheck(CheckParam var1, IFmlMonitor var2);

    @Deprecated
    public String allCheckAsync(CheckParam var1);

    public String batchCheck(CheckParam var1, IFmlMonitor var2);

    @Deprecated
    public String batchCheckAsync(CheckParam var1);
}

