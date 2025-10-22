/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.service;

import com.jiuqi.nr.data.logic.api.param.CheckExeResult;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.internal.obj.CheckExeParam;

public interface ICheckExecuteService {
    @Deprecated
    public String execute(CheckParam var1, IFmlMonitor var2, ActionEnum var3, String var4);

    public CheckExeResult execute(CheckParam var1, CheckExeParam var2);
}

