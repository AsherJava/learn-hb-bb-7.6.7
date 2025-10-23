/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nvwa.subsystem.core.model.SubServer
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.transmission.data.intf.TransmissionDataParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nvwa.subsystem.core.model.SubServer;

public interface ITransmissionDataService {
    public TransmissionResult pushData(TransmissionDataParam var1, SubServer var2, AsyncTaskMonitor var3) throws Exception;
}

