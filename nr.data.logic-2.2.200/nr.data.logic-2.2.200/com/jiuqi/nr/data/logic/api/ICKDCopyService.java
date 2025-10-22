/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api;

import com.jiuqi.nr.data.logic.api.param.ckdcopy.CopyDesParam;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.CopyDesResult;
import com.jiuqi.nr.data.logic.spi.ICopyDesMonitor;
import org.springframework.lang.NonNull;

public interface ICKDCopyService {
    public CopyDesResult copy(CopyDesParam var1);

    public CopyDesResult copy(CopyDesParam var1, @NonNull ICopyDesMonitor var2);
}

