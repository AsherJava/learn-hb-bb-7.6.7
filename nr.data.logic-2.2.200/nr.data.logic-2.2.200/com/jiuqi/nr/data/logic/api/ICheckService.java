/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api;

import com.jiuqi.nr.data.logic.api.param.CheckExeResult;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.spi.ICheckMonitor;

public interface ICheckService {
    public CheckResult check(CheckParam var1);

    public CheckExeResult allCheck(CheckParam var1);

    public CheckExeResult allCheck(CheckParam var1, ICheckMonitor var2);

    public CheckExeResult batchCheck(CheckParam var1);

    public CheckExeResult batchCheck(CheckParam var1, ICheckMonitor var2);
}

