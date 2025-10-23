/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.sbdata.carry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.sbdata.carry.bean.DataTableCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownDTO;
import com.jiuqi.nr.sbdata.carry.bean.TzClearDataParam;

public interface TzCarryDownService {
    public DataTableCarryResult clearData(TzClearDataParam var1);

    public DataTableCarryResult clearData(TzClearDataParam var1, AsyncTaskMonitor var2);

    public DataTableCarryResult carryDown2NextYear(TzCarryDownDTO var1);

    public DataTableCarryResult carryDown2NextYear(TzCarryDownDTO var1, AsyncTaskMonitor var2);
}

