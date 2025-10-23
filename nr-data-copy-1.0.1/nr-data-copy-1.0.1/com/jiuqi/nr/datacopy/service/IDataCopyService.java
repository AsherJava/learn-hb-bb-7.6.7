/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacopy.service;

import com.jiuqi.nr.datacopy.param.CopyDataParam;
import com.jiuqi.nr.datacopy.param.DataCopyReturnInfo;
import com.jiuqi.nr.datacopy.param.monitor.IDataCopyMonitor;

public interface IDataCopyService {
    public DataCopyReturnInfo pushData(CopyDataParam var1);

    public DataCopyReturnInfo pushData(CopyDataParam var1, IDataCopyMonitor var2);

    public DataCopyReturnInfo pullData(CopyDataParam var1);

    public DataCopyReturnInfo pullData(CopyDataParam var1, IDataCopyMonitor var2);
}

