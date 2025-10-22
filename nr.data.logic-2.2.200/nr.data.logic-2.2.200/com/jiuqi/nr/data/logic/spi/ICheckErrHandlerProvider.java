/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.api.param.CheckExeContext;
import com.jiuqi.nr.data.logic.spi.ICheckErrHandler;

public interface ICheckErrHandlerProvider {
    public ICheckErrHandler getCheckErrHandler(CheckExeContext var1);
}

