/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.api.param.ckdcopy.UnsupportHandleParam;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.UnsupportHandleResult;
import com.jiuqi.nr.data.logic.spi.ICopyDesMonitor;

public interface IUnsupportedDesHandler {
    public UnsupportHandleResult handleUnsupportedDes(UnsupportHandleParam var1, ICopyDesMonitor var2);
}

